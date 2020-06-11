package com.enzoroiz.challenge1

fun main(args: Array<String>) {
    val firstVariable = "Hello"
    val secondVariable = "Hello"

    println(firstVariable === secondVariable)

    println(firstVariable == secondVariable)

    var intVariable = 2988

    val anyVariable: Any = "Value here"
    if (anyVariable is String) {
        println(anyVariable.toUpperCase())
    }

    println("""1   1
            1  11
            1 111
            11111""".trimMargin("1"))
}