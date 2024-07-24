package com.arjix.http

class HttpHeaders {
    private val headers: MutableMap<String, MutableList<String>> = mutableMapOf()

    constructor()
    constructor(headers: Map<String, String>) {
        for ((key, value) in headers) {
            this.put(key, value)
        }
    }

    companion object {
        fun parseLines(lines: List<String>): HttpHeaders {
            val httpHeaders = HttpHeaders()

            for (line in lines) {
                val headerName = line.substringBefore(":")
                val headerValue = line.substringAfter(":").trim()

                httpHeaders.put(headerName, headerValue)
            }

            return httpHeaders
        }
    }

    fun put(key: String, value: String) {
        headers
            .getOrPut(key) { mutableListOf() }
            .add(value)
    }

    fun putAll(headers: Map<String, List<String>>) {
        for ((key, values) in headers) {
            for (value in values) {
                this.put(key, value)
            }
        }
    }

    fun putAll(headers: HttpHeaders) {
        for ((key, values) in headers.getAll()) {
            for (value in values) {
                this.put(key, value)
            }
        }
    }

    fun set(key: String, value: String) {
        headers[key] = mutableListOf(value)
    }

    fun has(key: String): Boolean {
        return headers.containsKey(key)
    }

    fun get(key: String): Array<String> {
        return headers.getOrDefault(key, emptyList()).toTypedArray()
    }

    fun getAll(): Map<String, Array<String>> {
        return headers.mapValues { it.value.toTypedArray() }
    }

    fun delete(key: String) {
        headers.remove(key)
    }

    override fun toString(): String {
        return "HttpHeaders $headers"
    }
}
