package com.enzoroiz.challenge3

fun main(args: Array<String>) {
    for (i in 5..5000 step 5) {
        print("$i, ")
    }

    println()

    for (i in -500..0) {
        print("$i, ")
    }

    var number = 0;
    var result = 1;
    print("$number, $result, ")
    for (i in 3..15) {
        val aux = result
        result += number
        number = aux
        print("$result, ")
    }

    iLoop@ for (i in 1..5) {
        println(i)
        jLoop@ for (j in 11..20) {
            if (i > 1) break@iLoop
            println(j)
            for (k in 100 downTo 90) {
                if (k < 98) break@jLoop
                println(k)
            }
        }
    }

    val num = 100
    val dNum = when {
        num > 100 -> -234.567
        num < 100 -> 4444.555
        else -> 0.0
    }

    println(dNum)
}