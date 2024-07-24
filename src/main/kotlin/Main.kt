package com.arjix

import com.arjix.controllers.Root
import com.arjix.http.*
import java.net.ServerSocket
import java.net.Socket
import java.util.Optional
import java.util.Scanner
import kotlin.concurrent.thread

fun main() {
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
            continue
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
            continue
        }

        val root = Root()
        val path = req.uri.path.replace(Regex("/$"), "")
        val pathParts = path.split("/").toTypedArray()

        val match = root.matches(pathParts)
        if (match.isFailure) {
            val response = when (val exception = match.exceptionOrNull()!!) {
                // TODO: Handle more errors
                else -> HttpResponse.Builder()
                    .status(HttpStatus.NotFound)
                    .body("No matching controller was found.".toByteArray())
                    .build()
            }

            response.write(output)
            continue
        }

        val controller = match.getOrThrow()
        if (!controller.methods.containsKey(req.method)) {
            HttpResponse.Builder()
                .status(HttpStatus.MethodNotAllowed)
                .headers(HttpHeaders(mapOf(
                    "Allow" to controller.methods.keys.joinToString(", ") { it.name }
                )))
                .build()
                .write(output)
            continue
        }

        try {
            val response = controller.methods[req.method]!!()
            response.write(output)
        } catch (e: Exception) {
            HttpResponse.Builder()
                .status(HttpStatus.InternalServerError)
                .build()
                .write(output)
        }
    }

    if (!conn.isClosed)
        conn.close()

    println("client $client disconnected!")
}
