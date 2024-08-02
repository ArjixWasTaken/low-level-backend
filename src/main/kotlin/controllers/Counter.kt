package com.arjix.controllers

import com.arjix.http.HttpStatus
import com.arjix.http.HttpVerb
import com.arjix.magic.Controller
import com.arjix.magic.HttpMapping
import com.arjix.magic.ResponseEntity
import kotlinx.serialization.Serializable

@Serializable
data class Counter(val count: Int);


@Controller("/counter")
class CounterController {
    var counter: Int = 0

    @HttpMapping(HttpVerb.GET, "/")
    fun getStudentById(): ResponseEntity<Counter> {
        this.counter++

        return ResponseEntity(HttpStatus.OK, null, Pair(Counter(this.counter), Counter.serializer()))
    }
}
