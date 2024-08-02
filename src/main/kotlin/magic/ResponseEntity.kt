package com.arjix.magic

import com.arjix.http.HttpHeaders
import com.arjix.http.HttpResponse
import com.arjix.http.HttpStatus
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.OutputStream


data class ResponseEntity<T>(
    val status: HttpStatus,
    val headers: HttpHeaders? = null,
    val data: Pair<T, KSerializer<T>>? = null,
) {
    fun writeToStream(stream: OutputStream) {
        val body = data?.let {
            Json.encodeToString(it.second, it.first).toByteArray()
        } ?: byteArrayOf()

        val headers = headers ?: HttpHeaders()
        headers.set("Content-Type", "application/json; charset=utf-8")

        HttpResponse(status, headers, body).write(stream)
    }
}
