package com.arjix.controllers

import com.arjix.http.HttpStatus
import com.arjix.http.HttpVerb
import com.arjix.magic.Controller
import com.arjix.magic.HttpMapping
import com.arjix.magic.PathVariable
import com.arjix.magic.ResponseEntity
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Int,
    val name: String,
);


@Controller("/student")
class StudentController {
    @HttpMapping(HttpVerb.GET, "/{id:Int}/{name:Alphanumeric}")
    fun getStudentById(
        @PathVariable("id") id: Int,
        @PathVariable("name") name: String,
    ): ResponseEntity<Student> {
        val data = Student(id, name)
        return ResponseEntity(HttpStatus.OK, null, Pair(data, Student.serializer()))
    }
}
