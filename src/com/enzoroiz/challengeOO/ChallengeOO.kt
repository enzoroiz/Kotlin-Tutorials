package academy.learnprogramming.oochallenge

import academy.learnprogramming.javacode.MountainBike

open class KotlinBicycle(var cadence: Int, var speed: Int, var gear: Int = 73) {
    fun applyBrake(decrement: Int) {
        speed -= decrement
    }

    fun speedUp(increment: Int) {
        speed += increment
    }

    open fun printDescription() {
        println("Bike is in gear $gear" +
                " with a cadence $cadence" +
                " travelling at a speed of $speed")
    }
}

class KotlinMountainBike(var seatHeight: Int, cadence: Int, speed: Int) :
        KotlinBicycle(cadence, speed) {

    constructor(seatHeight: Int, cadence: Int, speed: Int, gear: Int) :
        this(seatHeight, cadence, speed) {
        this.gear = gear
    }

    constructor(seatHeight: Int, cadence: Int, speed: Int, color: String) :
            this(seatHeight, cadence, speed) {
        println("Your bike color is $color")
    }

    companion object {
        var availableColors: List<String> = listOf("blue", "red", "green", "black", "white", "brown")
    }

    override fun printDescription() {
        super.printDescription()
        println("The mountain bike has a seat height of $seatHeight inches")
    }
}

class KotlinRoadBike(val tireWidth: Int, cadence: Int, speed: Int) :
        KotlinBicycle(cadence, speed) {

    constructor(tireWidth: Int, cadence: Int, speed: Int, gear: Int) :
            this(tireWidth, cadence, speed) {
        this.gear = gear
    }

    override fun printDescription() {
        super.printDescription()
        println("The road bike has a tire width of $tireWidth mm")
    }

}

fun main(args: Array<String>) {
    val bike = KotlinBicycle(10, 10, 10)
    bike.printDescription()

    val mountainBike = KotlinMountainBike(5, 10, 15, 20)
    mountainBike.printDescription()

    val roadBike = KotlinRoadBike(25, 13, 12, 42)
    roadBike.printDescription()

    val bike2 = KotlinBicycle(10, 10)
    bike2.printDescription()

    val mountainBike2 = KotlinMountainBike(5, 10, 15)
    mountainBike2.printDescription()

    val roadBike2 = KotlinRoadBike(25, 13, 12)
    roadBike2.printDescription()

    val blueMountainBike = KotlinMountainBike(5, 10, 15, "blue")
    mountainBike2.printDescription()

    KotlinMountainBike.availableColors.forEach { println(it) }
    KotlinMountainBike.availableColors = arrayListOf("purple", "pink")
    KotlinMountainBike.availableColors.forEach { println(it) }

}