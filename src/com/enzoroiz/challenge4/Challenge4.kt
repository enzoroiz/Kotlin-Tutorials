package com.enzoroiz.challenge4

fun main(args: Array<String>) {
    val joe = Person("Joe", "Jones", 45)
    val jane = Person("Jane", "Smith", 31)
    val mary = Person("Mary", "Wilson", 13)
    val john = Person("John", "Adams", 70)
    val jean = Person("Jean", "Smithson", 41)

    val (fName, lName, age) = joe
    println("fName = $fName, lName = $lName, age = $age")

    val people = mapOf(
        joe.lastName to joe,
        jane.lastName to jane,
        mary.lastName to mary,
        john.lastName to john,
        jean.lastName to jean
    )

    val lastNameWithS = people.count { it.key.startsWith("S") }
    println(lastNameWithS)

    val listOfNames = people.map { Pair(it.value.lastName, it.value.firstName) }.toList()
    println(listOfNames)

    people.also { it.values.map { entry -> println("${entry.firstName} is ${entry.age} years old") } }

    val list1 = listOf(1, 4, 9, 15, 33)
    val list2 = listOf(4, 55, -83, 15, 22, 101)
    val onBothLists = list1.filter { it in list2 }
    println(onBothLists)

    val regularPaper = Box<Regular>()
    var paper = Box<Paper>()
    paper = regularPaper
}

data class Person(val firstName: String, val lastName: String, val age: Int) {

}

class Box<out T> { // can only read from
    // T: out position
    // fun takePaper() : T { }
}

//class Box<in T> { // can only write to
    // T: in position
    // fun addPaper(element: T) { }
//}

open class Paper {

}

class Regular : Paper() {

}

class Premium : Paper() {

}

class Challenge4 {
}