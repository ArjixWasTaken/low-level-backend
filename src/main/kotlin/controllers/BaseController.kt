package com.arjix.controllers

import com.arjix.http.HttpVerb
import com.arjix.http.HttpResponse
import java.util.*

abstract class BaseController {
    open val name: String = ""
    open val methods: EnumMap<HttpVerb, () -> HttpResponse> = EnumMap(HttpVerb::class.java)

    open val children: List<BaseController> = emptyList()

    fun matches(path: Array<String>): Result<BaseController> {
        if (path.isEmpty()) {
            if (this.name.isBlank()) return Result.failure(Error("Not found"))
            else return Result.success(this)
        }

        val parts = path.toMutableList()

        val first = parts.removeFirst()
        if (first != this.name) return Result.failure(Error("Not found"))

        if (parts.isNotEmpty()) {
            for (child in children) {
                val match = child.matches(parts.toTypedArray())
                if (match.isSuccess) {
                    return match
                }
            }
        }

        if (parts.isEmpty()) {
            return Result.success(this)
        } else {
            return Result.failure(Error("Not found"))
        }
    }
}
