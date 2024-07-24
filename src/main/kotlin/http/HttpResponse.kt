package com.arjix.http

import java.io.OutputStream

data class HttpResponse(
    val status: HttpStatus,
    val headers: HttpHeaders,
    val body: ByteArray,
) {
    class Builder {
        var status = HttpStatus.OK
        var headers: HttpHeaders = HttpHeaders()
        var body: ByteArray = "".toByteArray()

        fun status(code: HttpStatus) = apply { this.status = code }
        fun headers(headers: HttpHeaders) = apply { this.headers = headers }
        fun body(body: ByteArray) = apply { this.body = body }

        fun build() = HttpResponse(status, headers, body)
    }

    fun write(stream: OutputStream): Boolean {
        var output = "HTTP/1.1 ${status.code} ${status.message}\r\n"

        val contentLength = body.size
        if (!headers.has("Content-Length")) {
            headers.set("Content-Length", contentLength.toString())
        }

        for ((header, values) in headers.getAll()) {
            for (value in values) {
                if (value.isBlank()) continue
                output += "$header: $value\r\n"
            }
        }

        output += "\r\n"

        val bytes = output.toByteArray() + body
        stream.write(bytes)

        return false
    }
}
