package com.enzoroiz.patterns.structural

import com.enzoroiz.patterns.structural.CarModel.*
import com.enzoroiz.patterns.structural.GamingCarFactory.getCarInstance
import kotlin.random.Random

fun main(args: Array<String>) {
    val colors = listOf("Red", "Blue", "Yellow", "Green", "White")
    val carModels = listOf(FERRARI_ENZO, LAMBORGHINI_GALLARDO, PORSCHE_CARRERA_GT, BUGATTI_VEYRON)
    val names = listOf("Enzo", "Michael", "Mika", "Felipe", "Lewis", "Ayrton", "Eddie", "Fernando", "Jackie", "Rubens")
    val surnames = listOf("Roiz", "Schumacher", "Hakkinen", "Massa", "Hamilton", "Senna", "Irvine", "Alonso", "Villeneuve", "Barrichello")
    val carsInTheRace = 50
    val cars = mutableListOf<CustomGamingCar>()

    for (i in 0..carsInTheRace) {
        cars.add(
            CustomGamingCar(
                car = getCarInstance(carModels[Random.nextInt(0, carModels.size)]),
                color = colors[Random.nextInt(0, colors.size)],
                pilot = "${names[Random.nextInt(0, names.size)]} ${surnames[Random.nextInt(0, names.size)]}"
            )
        )
    }

    println("############################")
    cars.forEach {
        println(it.toString())
        println("############################")
    }
}

// Flyweight pattern intends to save memory when lots of objects of the same type need to be created
// The idea is to have a set of objects that can be created once and returned under request from (cache) / shared
// These objects can have intrinsic state which is data / actions shared amongst all objects (model, price, maxSpeed)
// Also they can have extrinsic state which is data / actions specific to that object and not to the shared instance (color, pilot)
// This pattern uses another pattern, which is a ObjectFactory for the shared objects.

enum class CarModel(val modelName: String) {
    FERRARI_ENZO("Ferrari Enzo"),
    LAMBORGHINI_GALLARDO("Lamborghini Gallardo"),
    PORSCHE_CARRERA_GT("Porsche Carrera GT"),
    BUGATTI_VEYRON("Bugatti Veyron")
}

interface GamingCar {
    val maxSpeed: Int
    val price: Int
    val model: CarModel
}

class CustomGamingCar(val car: GamingCar, private val color: String, private val pilot: String): GamingCar by car {
    override fun toString(): String {
        return """
            Pilot: $pilot
            Model: ${car.model.modelName}
            Max Speed: ${car.maxSpeed} km/h
            Price: ${car.price}
            Color: $color
            """.trimIndent()
    }
}

class FerrariEnzo: GamingCar {
    override val maxSpeed: Int = 320
    override val price: Int = 1000000
    override val model: CarModel = FERRARI_ENZO
}

class PorscheCarreraGT: GamingCar {
    override val maxSpeed: Int = 340
    override val price: Int = 500000
    override val model: CarModel = PORSCHE_CARRERA_GT
}

class LamborghiniGallardo: GamingCar {
    override val maxSpeed: Int = 360
    override val price: Int = 2000000
    override val model: CarModel = LAMBORGHINI_GALLARDO
}

class BugattiVeyron: GamingCar {
    override val maxSpeed: Int = 420
    override val price: Int = 5000000
    override val model: CarModel = BUGATTI_VEYRON
}

object GamingCarFactory {
    private val cars =  mutableMapOf<CarModel, GamingCar>()

    fun getCarInstance(model: CarModel): GamingCar {
        return cars[model] ?: let {
            println("Model ${model.modelName} not found inside the cache. Creating it right now.")
            when (model) {
                FERRARI_ENZO -> FerrariEnzo().apply { cars[FERRARI_ENZO] = this }
                LAMBORGHINI_GALLARDO -> LamborghiniGallardo().apply { cars[LAMBORGHINI_GALLARDO] = this }
                PORSCHE_CARRERA_GT -> PorscheCarreraGT().apply { cars[PORSCHE_CARRERA_GT] = this }
                BUGATTI_VEYRON -> BugattiVeyron().apply { cars[BUGATTI_VEYRON] = this }
            }
        }
    }
}