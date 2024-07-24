package com.arjix.controllers

import java.net.URI
import java.util.Optional

class Student: BaseController() {
    override val name = "student"
    override val children = listOf(StudentHomework())
}
