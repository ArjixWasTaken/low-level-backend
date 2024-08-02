package com.arjix.nodes

enum class PathVariableType {
    Int,
    Alphanumeric;

    fun validate(param: kotlin.String): Boolean = when (this) {
        Int -> param.toIntOrNull() != null
        Alphanumeric -> param.all { it.isLetter() || it.isDigit() }
    }
}

data class PathVariable(val pattern: String): Node("val ${pattern.substring(1, pattern.length - 1).replace(":", ": ")}") {
    var vName: String
    var vType: PathVariableType

    init {
        val parts = pattern.substring(1, pattern.length - 1).split(":")
        if (parts.size != 2) {
            throw Exception("Invalid PathVariable pattern")
        }

        val (pName, pType) = parts

        vName = pName.trim()
        vType = PathVariableType.valueOf(pType.trim())
    }

    override fun matches(path: Array<String>): Result<NodePath> {
        val nodePath = NodePath()

        if (path.isEmpty()) return Result.failure(Error("Not found"))
        val parts = path.toMutableList()

        val param = parts.removeFirst()
        if (!vType.validate(param)) {
            return Result.failure(Error("Not found"))
        }

        nodePath.add(this, param)

        if (parts.isEmpty()) return Result.success(nodePath)

        for (child in children) {
            val match = child.matches(parts.toTypedArray())
            if (match.isSuccess) {
                return Result.success(nodePath.merge(match.getOrThrow()))
            }
        }

        return Result.failure(Error("Not found"))
    }
}
