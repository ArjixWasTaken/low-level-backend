package com.arjix.http

class HttpVersionNotSupported: Error()
data class InvalidHttpMethod(val method: String): Error()
