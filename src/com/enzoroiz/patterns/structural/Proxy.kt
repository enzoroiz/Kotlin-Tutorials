package com.enzoroiz.patterns.structural

fun main(args: Array<String>) {
    val guests = listOf("Enzo Roiz", "Jenson Button", "Mika Hakkinen", "Felipe Massa", "Lewis Hamilton")
    val vipParty = VIPParty("F1 Pilots 2021", guests)
    vipParty.apply {
        joinEvent("James Bond")
        guests.forEach { joinEvent(it) }
        joinEvent("David Beckham")
    }

    println("############################")
    guests.forEach { vipParty.leaveEvent(it) }
}

// Proxy pattern aims to provide a controlled access to a functionality of a class
// It serves as an intermediary (VIPParty) to the target object (Party)
// Allowing one to perform operations in advance before getting to the real object (checking the guests list)

interface Event {
    fun joinEvent(guest: String)
    fun leaveEvent(guest: String)
}

class Party(private val partyName: String): Event {
    override fun joinEvent(guest: String) {
        println("Welcome to $partyName, $guest!")
    }

    override fun leaveEvent(guest: String) {
        println("Bye, $guest! I hope you have enjoyed $partyName!")
    }
}

class VIPParty(private val name: String, private val guestList: List<String>): Event {
    private val party = Party(name)

    override fun joinEvent(guest: String) {
        if (guestList.contains(guest)) {
            party.joinEvent(guest)
        } else {
            println("I beg your pardon, but the name $guest is not on the guest list.")
        }
    }

    override fun leaveEvent(guest: String) {
        party.leaveEvent(guest)
    }
}