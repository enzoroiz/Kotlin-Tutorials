package com.enzoroiz.challenge2

fun main(args: Array<String>) {
    println(MyClass.accessTest())
}

class MyClass {
    companion object SomeCompanion {
        private var test = 6
        fun accessTest() = "I am accessing test = $test"
    }
}