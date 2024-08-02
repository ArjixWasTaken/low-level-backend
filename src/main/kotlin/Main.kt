package com.arjix

import com.arjix.http.*
import com.arjix.magic.*
import com.arjix.nodes.Node
import com.arjix.nodes.NodePath
import com.arjix.nodes.PathVariableType
import com.arjix.nodes.createRouteTree
import org.reflections.Reflections
import java.net.ServerSocket
import java.net.Socket
import java.net.URI
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.concurrent.thread

var rootNode: Node? = null

fun main() {
    val packageName = ::main.javaClass.packageName
    val reflections = Reflections(packageName)

    val container = HashMap<Class<*>, Any>()
    val routes = ArrayList<Triple<String, HttpVerb, (HttpRequest, NodePath) -> ResponseEntity<*>>>()

    val controllers = reflections.getTypesAnnotatedWith(Controller::class.java)
    for (controller in controllers) {
        val controllerInfo = controller.getAnnotation(Controller::class.java)
        container[controller] = controller.getDeclaredConstructor().newInstance()

        val methods = controller.getMethodsAnnotatedWith(HttpMapping::class.java)
        for ((methodInfo, method) in methods) {
            val route = controllerInfo.path.trimEnd('/') + '/' + methodInfo.path.trimStart('/')
            if (method.returnType != ResponseEntity::class.java) {
                throw Error("${controller.name}::${method.name}() must return ResponseEntity")
            }

            routes.add(Triple(route, methodInfo.method) { req, nodePath ->
                val params = arrayListOf<Any?>()

                for (param in method.parameters) {
                    if (param.isAnnotationPresent(PathVariable::class.java)) {
                        val pathVariable = param.getAnnotation(PathVariable::class.java).name
                        val nodeIdx = nodePath.nodes.indexOfFirst { when (it) {
                            is com.arjix.nodes.PathVariable -> it.vName == pathVariable
                            else -> false
                        } }

                        if (nodeIdx == -1) throw IllegalStateException()

                        val value = nodePath.parts[nodeIdx]
                        when ((nodePath.nodes[nodeIdx] as com.arjix.nodes.PathVariable).vType) {
                            PathVariableType.Int -> params.add(value.toIntOrNull())
                            PathVariableType.Alphanumeric -> params.add(value)
                        }
                    } else {
                        throw NotImplementedError()
                    }
                }

                method.invoke(container[controller], *params.toTypedArray()) as ResponseEntity<*>
            })
        }
    }

    rootNode = createRouteTree(routes)


    val server = ServerSocket(6969)
    println("Server is running on port ${server.localPort}")

    while (true) {
        val connection = server.accept()
        thread { handleConnection(connection) }
    }
}

// These verbs are not supported because they have a body, and we currently do not support request bodies.
val unsupportedVerbs = listOf(
    HttpVerb.POST,
    HttpVerb.PUT,
    HttpVerb.PATCH,
    HttpVerb.DELETE,
    HttpVerb.OPTIONS,
    HttpVerb.TRACE,
    HttpVerb.CONNECT,
)

fun handleConnection(conn: Socket) {
    val client = "${conn.inetAddress.canonicalHostName}:${conn.port}"
    println("client $client connected!")

    val input = Scanner(conn.getInputStream())
    val output = conn.getOutputStream()

    // TODO: Comply with keep-alive header

    while (!conn.isClosed) {
        var request: Optional<Result<HttpRequest>>

        try { request = HttpRequest.read(input) }
        catch (e: NoSuchElementException) {
            break
        }

        if (request.isEmpty) continue

        val result = request.get()
        if (result.isFailure) {
            val response = when (val exception = result.exceptionOrNull()!!) {
                is HttpVersionNotSupported -> HttpResponse.Builder()
                    .status(HttpStatus.HTTPVersionNotSupported)
                    .build()

                is InvalidHttpMethod -> HttpResponse.Builder()
                    .status(HttpStatus.UnprocessableContent)
                    .body("Unknown HTTP verb: ${exception.method}\n".toByteArray())
                    .build()

                else -> HttpResponse.Builder()
                    .status(HttpStatus.InternalServerError)
                    .build()
            }

            response.write(output)
            break
        }

        val req = result.getOrThrow()
        if (unsupportedVerbs.contains(req.method)) {
            HttpResponse.Builder()
                .status(HttpStatus.MethodNotAllowed)
                .headers(HttpHeaders(mapOf(
                    "Allow" to HttpVerb.entries
                        .filter { !unsupportedVerbs.contains(it) }
                        .joinToString(", ") { it.name }
                )))
                .build()
                .write(output)
            break
        }

        val path = req.uri.path.replace(Regex("/$"), "")
        val pathParts = path.split("/").toTypedArray()

        val match = rootNode!!.matches(pathParts)
        if (match.isFailure) {
            val response = when (val exception = match.exceptionOrNull()!!) {
                // TODO: Handle more errors
                else -> HttpResponse.Builder()
                    .status(HttpStatus.NotFound)
                    .body("No matching controller was found.".toByteArray())
                    .build()
            }

            response.write(output)
            break
        }

        val nodePath = match.getOrThrow()
        val node = nodePath.nodes.last()

        if (!node.methods.containsKey(req.method)) {
            HttpResponse.Builder()
                .status(HttpStatus.MethodNotAllowed)
                .headers(HttpHeaders(mapOf(
                    "Allow" to node.methods.keys.joinToString(", ") { it.name }
                )))
                .build()
                .write(output)
            break
        }

        try {
            val response = node.methods[req.method]!!(req, nodePath)
            response.writeToStream(output)
        } catch (e: Exception) {
            HttpResponse.Builder()
                .status(HttpStatus.InternalServerError)
                .build()
                .write(output)
        }

        break
    }

    if (!conn.isClosed)
        conn.close()

    println("client $client disconnected!")
}
