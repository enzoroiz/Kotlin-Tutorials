package com.enzoroiz.patterns.structural

fun main(args: Array<String>) {
    val carPricePerKm = 7.0
    val kickScooterPricePerKm = 3.0
    val km = 10
    val helmetPrice = 10.0

    val carRentCalculator = VehicleRentCalculator(carPricePerKm)
    println("Rental Price is: EUR ${carRentCalculator.calculatePrice(km)}")
    println("############################")

    val rentalWithInsurance = RentalInsurance(carRentCalculator)
    println("Rental Price is: EUR ${rentalWithInsurance.calculatePrice(km)}")
    println("############################")

    val rentalWithRentalExtraHelmet = RentalExtraHelmet(helmetPrice, rentalWithInsurance)
    println("Rental Price is: EUR ${rentalWithRentalExtraHelmet.calculatePrice(km)}")
    println("############################")

    val rentalWithHalfPrice = RentalHalfPrice(rentalWithRentalExtraHelmet)
    println("Rental Price is: EUR ${rentalWithHalfPrice.calculatePrice(km)}")
    println("############################")

    val kickScooterRentCalculator = VehicleRentCalculator(kickScooterPricePerKm)
    println("Rental Price is: EUR ${kickScooterRentCalculator.calculatePrice(km)}")
    println("############################")

    val kickScooterWithExtraHelmet = RentalExtraHelmet(helmetPrice, kickScooterRentCalculator)
    println("Rental Price is: EUR ${kickScooterWithExtraHelmet.calculatePrice(km)}")

}

// Decorator pattern allows to add functionality to an object dynamically
// Thus without changing a class and therefore without changing any other objects
// Follows the open for extension / closed for modification principle
// Each extra is a decorator (added functionality) of the RentCalculator.
// Through the decorators one can extend the calculatePrice() functionality of the RentCalculator

interface RentCalculator {
    val pricePerKm: Double
    fun calculatePrice(km: Int): Double = km * pricePerKm
}

class VehicleRentCalculator(override val pricePerKm: Double): RentCalculator

abstract class RentalExtra(rentCalculator: RentCalculator): RentCalculator by rentCalculator

class RentalInsurance(rentCalculator: RentCalculator): RentalExtra(rentCalculator) {
    override fun calculatePrice(km: Int): Double {
        println("Adding Extra Insurance: Free!")
        return super.calculatePrice(km)
    }
}

class RentalExtraHelmet(private val helmetPrice: Double, rentCalculator: RentCalculator): RentalExtra(rentCalculator) {
    override fun calculatePrice(km: Int): Double {
        println("Adding Extra Helmet: EUR $helmetPrice")
        return super.calculatePrice(km) + helmetPrice
    }
}

class RentalHalfPrice(rentCalculator: RentCalculator): RentalExtra(rentCalculator) {
    override fun calculatePrice(km: Int): Double {
        println("Adding Rental Half Price")
        return super.calculatePrice(km) / 2
    }
}