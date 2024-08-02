package com.arjix.nodes

import com.arjix.http.HttpRequest
import com.arjix.http.HttpVerb
import com.arjix.magic.ResponseEntity
import java.util.*
import kotlin.collections.ArrayList

open class Node(open val name: String) {

    val children: ArrayList<Node> = ArrayList()
    val methods: EnumMap<HttpVerb, (HttpRequest, NodePath) -> ResponseEntity<*>> = EnumMap(HttpVerb::class.java)

    override fun toString(): String {
        return "Node(name='$name')"
    }

    open fun matches(path: Array<String>): Result<NodePath> {
        val nodePath = NodePath()

        if (path.isEmpty()) {
            if (this.name.isBlank()) return Result.failure(Error("Not found"))
            else return Result.success(nodePath)
        }

        val parts = path.toMutableList()

        val first = parts.removeFirst()
        if (first != this.name) return Result.failure(Error("Not found"))

        nodePath.add(this, first)

        if (parts.isEmpty()) return Result.success(nodePath)

        for (child in children) {
            val match = child.matches(parts.toTypedArray())
            if (match.isSuccess) {
                return Result.success(nodePath.merge(match.getOrThrow()))
            }
        }

        return Result.failure(Error("Not found"))
    }

    fun toASCII(): String {
        return buildString { buildString(this, "", true) }
    }

    private fun buildString(sb: StringBuilder, prefix: String, isTail: Boolean) {
        sb.append(prefix).append(if (isTail) "└── " else "├── ").append(name.ifBlank { "<root>" })

        if (methods.isNotEmpty()) {
            sb.append(" [Methods: ").append(methods.keys.joinToString(", ")).append("]")
        }

        sb.append("\n")

        for (i in 0 until children.size - 1) {
            children[i].buildString(sb, prefix + if (isTail) "    " else "│   ", false)
        }

        if (children.size > 0) {
            children[children.size - 1].buildString(sb, prefix + if (isTail) "    " else "│   ", true)
        }
    }
}


fun createRouteTree(routes: ArrayList<Triple<String, HttpVerb, (HttpRequest, NodePath) -> ResponseEntity<*>>>): Node {
    val root = Node("")

    for ((uri, verb, handler) in routes) {
        val parts = uri.split("/").filter { it.isNotEmpty() }
        addPath(root, parts, verb, handler)
    }

    return root
}

fun addPath(root: Node, parts: List<String>, verb: HttpVerb, handler: (HttpRequest, NodePath) -> ResponseEntity<*>) {
    var currentNode = root

    for (part in parts) {
        var childNode = currentNode.children.find { it.name == part }
        if (childNode != null) {
            currentNode = childNode
            continue
        }

        childNode = if (part.startsWith("{") && part.endsWith("}")) {
            PathVariable(part)
        } else {
            Node(part)
        }

        currentNode.children.add(childNode)
        currentNode = childNode
    }

    // Add the handler to the final node
    currentNode.methods[verb] = handler
}
