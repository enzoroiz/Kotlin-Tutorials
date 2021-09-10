package com.enzoroiz.patterns.creational

fun main(args: Array<String>) {
    // JAVA LIKE
    val macBook = Computer.ComputerBuilder("i7")
            .memory("8GB")
            .battery("12hrs / Lithium")
            .hardDisk("SSD 512GB")
            .build()

    val dellDesktop = Computer.ComputerBuilder("i3")
            .memory("4GB")
            .battery("Light Powered")
            .hardDisk("HD 512GB")
            .isDesktop(true)
            .build()


    println(macBook.toString())
    println("############################")
    println(dellDesktop.toString())

    // KOTLIN LIKE (named parameters / can be unordered)
    val advancedMacBook = AdvancedComputer(
        memory = "16GB",
        hardDisk = "SSD 1TB",
        battery = "24hrs / Cadmium",
        cpu = "i9"
    )

    val advancedDellDesktop = AdvancedComputer(
        isDesktop = true,
        memory = "8GB",
        hardDisk = "SSD 512GB",
        battery = "Light Powered",
        cpu = "i5"
    )

    println("############################")
    println(advancedMacBook.toString())
    println("############################")
    println(advancedDellDesktop.toString())
}

// JAVA LIKE BUILDER CLASS IN KOTLIN
class Computer private constructor(private val builder: ComputerBuilder) {
    private val cpu: String
    private val memory: String
    private val hardDisk: String
    private val battery: String
    private val isDesktop: Boolean

    init {
        cpu = builder.cpu
        memory = builder.memory
        hardDisk = builder.hardDisk
        battery = builder.battery
        isDesktop = builder.isDesktop
    }

    override fun toString(): String {
        return """
            DESKTOP: ${ if (isDesktop) "YES" else "NO" }
            CPU: $cpu
            MEMORY: $memory
            HD: $hardDisk
            BATTERY: $battery
        """.trimIndent()
    }

    class ComputerBuilder(val cpu: String) {
        lateinit var memory: String
            private set
        lateinit var hardDisk: String
            private set
        lateinit var battery: String
            private set
        var isDesktop: Boolean = false
            private set

        fun memory(memory: String) = apply { this.memory = memory }
        fun hardDisk(hardDisk: String) = apply { this.hardDisk = hardDisk }
        fun battery(battery: String) = apply { this.battery = battery }
        fun isDesktop(isDesktop: Boolean) = apply { this.isDesktop = isDesktop }
        fun build() = Computer(this)
    }
}

// INSTEAD OF USING A BUILDER LIKE THIS
// IT'S BETTER TO USE KOTLIN LIKE DATA CLASS TO ACHIEVE THE SAME

data class AdvancedComputer(
    val cpu: String,
    val memory: String,
    val hardDisk: String,
    val battery: String,
    val isDesktop: Boolean = false
) {
    override fun toString(): String {
        return """
            DESKTOP: ${ if (isDesktop) "YES" else "NO" }
            CPU: $cpu
            MEMORY: $memory
            HD: $hardDisk
            BATTERY: $battery
        """.trimIndent()
    }
}