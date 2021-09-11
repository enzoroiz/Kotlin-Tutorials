package com.enzoroiz.patterns.creational

fun main(args: Array<String>) {
    val onePlus3T = SmartPhoneCloneable(
            brand = "OnePlus",
            model = "3T",
            os = "Android",
            RAM = 6,
            storage = 64
    )

    // JAVA LIKE
    val onePlus5T = onePlus3T.clone().apply {
        model = "5T"
        storage = 128
    }

    val onePlus7PRO = SmartPhone(
            brand = "OnePlus",
            model = "7 PRO",
            os = "Android",
            RAM = 8,
            storage = 128
    )

    // Kotlin like
    val onePlus9T = onePlus7PRO.copy(
            model = "9T",
            RAM = 12
    )

    println("############################")
    println(onePlus3T)
    println("############################")
    println(onePlus5T)
    println("############################")
    println(onePlus7PRO)
    println("############################")
    println(onePlus9T)

}

// JAVA LIKE implementing the cloneable interface
class SmartPhoneCloneable(
        var brand: String,
        var model: String,
        var os: String,
        var RAM: Int,
        var storage: Int
): Cloneable {
    // Shallow copy
    // Returns a new instance with attributes that points to the original reference
    public override fun clone(): SmartPhoneCloneable {
        return SmartPhoneCloneable(
                this.brand,
                this.model,
                this.os,
                this.RAM,
                this.storage
        )
    }

    override fun toString(): String {
        return """
            BRAND: $brand
            MODEL: $model
            OS: $os
            MEMORY: $RAM
            STORAGE: $storage
        """.trimIndent()
    }
}

// KOTLIN LIKE
// IN KOTLIN WHILE COPYING YOU CAN SPECIFY WHICH
// PROPERTIES OF THE OBJECT YOU WANT TO UPDATE
data class SmartPhone(
        val brand: String,
        val model: String,
        val os: String,
        val RAM: Int,
        val storage: Int
) {
    override fun toString(): String {
        return """
            BRAND: $brand
            MODEL: $model
            OS: $os
            MEMORY: $RAM
            STORAGE: $storage
        """.trimIndent()
    }
}