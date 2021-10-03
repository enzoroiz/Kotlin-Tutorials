package com.enzoroiz.patterns.behavioural

import com.enzoroiz.patterns.behavioural.UserRegistrationFormData.UserRegistrationSavedState

fun main(args: Array<String>) {
    val userRegistrationFormData = UserRegistrationFormData()
    val userRegistrationFormStates = UserRegistrationFormStates()

    println("############################")
    println("Starting to fill in user form")
    userRegistrationFormData.name = "Enzo Roiz"
    userRegistrationFormStates.addState(userRegistrationFormData.saveUserRegistrationData())
    userRegistrationFormData.email = "enzo@test.com"
    userRegistrationFormStates.addState(userRegistrationFormData.saveUserRegistrationData())
    userRegistrationFormData.zipCode = "44135"
    userRegistrationFormStates.addState(userRegistrationFormData.saveUserRegistrationData())
    userRegistrationFormData.street = "Bruderweg 25"
    userRegistrationFormStates.addState(userRegistrationFormData.saveUserRegistrationData())
    userRegistrationFormData.city = "Dortmund"
    userRegistrationFormStates.addState(userRegistrationFormData.saveUserRegistrationData())
    userRegistrationFormData.country = "Germany"
    userRegistrationFormStates.addState(userRegistrationFormData.saveUserRegistrationData())
    userRegistrationFormData.phone = "+491747687643"
    userRegistrationFormStates.addState(userRegistrationFormData.saveUserRegistrationData())

    println()
    println()
    userRegistrationFormStates.getLastState()
    userRegistrationFormStates.getStateAtPosition(userRegistrationFormStates.getStatesCount() - 2)
    userRegistrationFormStates.getStateAtPosition(0)
    userRegistrationFormStates.getStateAtPosition(100)
}

// Memento Pattern has as its goal allowing the object to be restored to a previous state
// In order not to break the encapsulation, the previous states are accessible only within the object
// therefore no outside objects can alter a previously saved state.
// The Originator (UserRegistrationFormData) is the one whose data is saved.
// The Memento (UserRegistrationSavedState) holds the originator data, therefore, its state to be saved / restored.
// The Care Taker (UserRegistrationFormStates) holds the list of states (Mementos) that can be restored.

class UserRegistrationFormData(
    var name: String? = null,
    var email: String? = null,
    var zipCode: String? = null,
    var street: String? = null,
    var city: String? = null,
    var country: String? = null,
    var phone: String? = null
) {

    fun saveUserRegistrationData(): UserRegistrationSavedState {
        return UserRegistrationSavedState(name, email, zipCode, street, city, country, phone)
    }

    fun restoreSavedUserRegistrationData(userRegistrationSavedState: UserRegistrationSavedState) {
        this.name = userRegistrationSavedState.name
        this.email = userRegistrationSavedState.email
        this.zipCode = userRegistrationSavedState.zipCode
        this.street = userRegistrationSavedState.street
        this.city = userRegistrationSavedState.city
        this.country = userRegistrationSavedState.country
        this.phone = userRegistrationSavedState.phone
    }

    inner class UserRegistrationSavedState(
        val name: String? = null,
        val email: String? = null,
        val zipCode: String? = null,
        val street: String? = null,
        val city: String? = null,
        val country: String? = null,
        val phone: String? = null
    ) {
        override fun toString(): String {
            return """
                Name: $name
                Email: $email
                Zip Code: $zipCode
                Street: $street
                City: $city
                Country: $country
                Phone: $phone
            """.trimIndent()
        }
    }
}

class UserRegistrationFormStates {
    private val userRegistrationSavedStates = mutableListOf<UserRegistrationSavedState>()

    fun addState(state: UserRegistrationSavedState) {
        println("############################")
        println("Backing up user registration at state...")
        println(state)
        userRegistrationSavedStates.add(state)
    }

    fun getStateAtPosition(position: Int): UserRegistrationSavedState? {
        val state = userRegistrationSavedStates.getOrNull(position)

        println("############################")
        state?.let {
            println("Getting user registration state at position - $position...")
            println(it)
        } ?: println("There's no registered user form data.")

        return state
    }

    fun getLastState(): UserRegistrationSavedState? {
        val state = userRegistrationSavedStates.lastOrNull()

        println("############################")
        state?.let {
            println("Getting last user registration state")
            println(it)
        } ?: println("There's no registered user form data.")

        return state
    }

    fun getStatesCount(): Int {
        return userRegistrationSavedStates.size
    }
}

