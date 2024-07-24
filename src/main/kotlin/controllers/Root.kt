package com.arjix.controllers

import com.arjix.http.HttpVerb
import com.arjix.http.HttpHeaders
import com.arjix.http.HttpResponse
import com.arjix.http.HttpStatus
import java.util.*

class Root: BaseController() {
    override val children = listOf(Teacher(), Student())
    override val methods: EnumMap<HttpVerb, () -> HttpResponse> = EnumMap(mapOf(
        HttpVerb.GET to this::get,
        HttpVerb.HEAD to this::head
    ))

    fun get() = HttpResponse.Builder()
        .status(HttpStatus.OK)
        .body("Hi there!".toByteArray())
        .build()

    fun head() = HttpResponse.Builder()
        .status(HttpStatus.OK)
        .headers(HttpHeaders(mapOf("Content-Type" to "text/plain")))
        .build()
}
