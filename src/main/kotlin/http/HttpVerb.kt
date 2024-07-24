package com.arjix.http

import java.util.*

enum class HttpVerb {
    HEAD,
    GET,
    PATCH,
    POST,
    PUT,
    DELETE,
    OPTIONS,
    TRACE,
    CONNECT;

    companion object {
        fun parse(verb: String): Optional<HttpVerb> {
            val parsed = when (verb) {
                "HEAD" -> HEAD
                "GET" -> GET
                "PATCH" -> PATCH
                "POST" -> POST
                "PUT" -> PUT
                "DELETE" -> DELETE
                "OPTIONS" -> OPTIONS
                "TRACE" -> TRACE
                "CONNECT" -> CONNECT
                else -> null
            }

            return Optional.ofNullable(parsed)
        }
    }
}
