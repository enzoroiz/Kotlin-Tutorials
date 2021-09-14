package com.enzoroiz.patterns.structural

fun main(args: Array<String>) {
    val socketUK = UKElectricitySocket()
    val socketEU = EUEnergySocket()

    val connectorUK = UKPowerConnector()
    val connectorEU = EUPowerConnector()

    println("############################")
    socketUK.connect(connectorUK)
    println("############################")
    socketEU.plugIn(connectorEU)

    val connectorUKToEUAdapter = UKToEUConnectorAdapter(connectorUK)
    val connectorEUToUKAdapter = EUToUKConnectorAdapter(connectorEU)

    println("############################")
    socketUK.connect(connectorEUToUKAdapter)
    println("############################")
    socketEU.plugIn(connectorUKToEUAdapter)
}

interface UKConnector {
    fun provideElectricity()
}

class UKElectricitySocket() {
    fun connect(connector: UKConnector) {
        connector.provideElectricity()
    }
}

interface EUConnector {
    fun provideEnergy()
}

class EUEnergySocket() {
    fun plugIn(connector: EUConnector) {
        connector.provideEnergy()
    }
}

class UKPowerConnector: UKConnector {
    override fun provideElectricity() {
        println("Providing electricity as in UK")
    }
}

class EUPowerConnector: EUConnector {
    override fun provideEnergy() {
        println("Providing energy as in EU")
    }
}

class EUToUKConnectorAdapter(private val connector: EUConnector): UKConnector {
    override fun provideElectricity() {
        println("Adapting from EU to UK")
        connector.provideEnergy()
    }
}

class UKToEUConnectorAdapter(private val connector: UKConnector): EUConnector {
    override fun provideEnergy() {
        println("Adapting from UK to EU")
        connector.provideElectricity()
    }
}

