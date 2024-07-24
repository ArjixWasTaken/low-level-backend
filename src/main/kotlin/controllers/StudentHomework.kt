package com.arjix.controllers

import com.arjix.http.HttpResponse
import com.arjix.http.HttpStatus
import com.arjix.http.HttpVerb
import java.util.*

class StudentHomework: BaseController() {
    override val name = "homework"
    override val methods: EnumMap<HttpVerb, () -> HttpResponse> = EnumMap(mapOf(
        HttpVerb.GET to this::get,
    ))

    fun get() = HttpResponse.Builder()
        .status(HttpStatus.OK)
        .body("No homework!".toByteArray())
        .build()
}
