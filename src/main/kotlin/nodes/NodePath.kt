package com.arjix.nodes

class NodePath {
    val nodes = arrayListOf<Node>()
    val parts = arrayListOf<String>()

    fun add(node: Node, part: String) {
        nodes.add(node)
        parts.add(part)
    }

    fun merge(nodePath: NodePath): NodePath {
        nodes.addAll(nodePath.nodes)
        parts.addAll(nodePath.parts)

        return this
    }

    fun build(): List<Pair<Node, String>> {
        return nodes.mapIndexed { idx, node -> Pair(node, parts[idx]) }
    }
    override fun toString() = build().toString()
}
