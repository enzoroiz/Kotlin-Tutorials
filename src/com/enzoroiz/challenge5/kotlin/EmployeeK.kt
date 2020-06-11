@file:JvmName("KotlinStuff")
package com.enzoroiz.challenge5.kotlin

fun sayHelloToJava(name: String) {
    println("Kotlin says hello to Java and $name!")
}

object Challenge {
    @JvmStatic
    fun doMath(x: Int, y: Int) = (x + y) * (x - y)
}

class EmployeeK(val firstName: String, val lastName: String, @JvmField var startYear: Int) {
    @JvmOverloads
    fun takesDefault(param1: String, param2: String = "default") {
        println("$param1 and $param2")
    }
}