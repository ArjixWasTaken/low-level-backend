package com.arjix.http

import java.net.URI
import java.util.*
import kotlin.jvm.optionals.getOrNull

data class HttpRequest(
    val method: HttpVerb,
    val uri: URI,
    val headers: HttpHeaders
) {
    companion object {
        fun read(stream: Scanner): Optional<Result<HttpRequest>> {
            val lines = mutableListOf<String>()

            do {
                val line = stream.nextLine()
                if (line.trim().isEmpty()) break

                lines.add(line)
            } while (true);

            if (lines.isEmpty())
                return Optional.empty()

            val statusLine = lines.removeFirst()
            val (verb, uri, version) = statusLine.split(" ")
            if (version != "HTTP/1.1")
                return Optional.of(Result.failure(HttpVersionNotSupported()))

            val method = HttpVerb.parse(verb).getOrNull()
                ?: return Optional.of(Result.failure(InvalidHttpMethod(verb)))

            val headers = HttpHeaders.parseLines(lines)

            return Optional.of(Result.success(
                HttpRequest(
                    method=method,
                    uri=URI.create(uri),
                    headers=headers
                )
            ))
        }
    }
}
