package com.enzoroiz.patterns.behavioural

fun main() {
    val trackingManager = TrackingManager()
    val trackers = listOf(FirebaseTracker, FlurryTracker, BrazeTracker)

    for (tracker in trackers) {
        trackingManager.setUserId(tracker, "1")
        trackingManager.trackEvent(tracker, "app_opened", mapOf("time" to "23:59:59"))
    }
}

// Strategy Pattern goal is to allow the application to decide how the execution will be done at runtime
// Each event tracker is a different "strategy"
// The function track() in the TrackingManager receives a Tracker as a strategy and at runtime it will decide
// To which EventTracker the event is going to be sent

class TrackingManager {
    fun trackEvent(eventTracker: EventTracker, name: String, parameters: Map<String, String>) {
        eventTracker.trackEvent(name, parameters)
    }

    fun setUserId(eventTracker: EventTracker, id: String) {
        eventTracker.setUserId(id)
    }
}

interface EventTracker {
    fun setUserId(id: String)
    fun trackEvent(name: String, parameters: Map<String, String>)
}

object FirebaseTracker: EventTracker {
    override fun setUserId(id: String) {
        println("Firebase - Setting User ID: $id")
    }

    override fun trackEvent(name: String, parameters: Map<String, String>) {
        println("Firebase - Tracking Event: $name")
        println("Firebase - Parameters:")
        println(parameters.toString())
    }
}

object FlurryTracker: EventTracker {
    override fun setUserId(id: String) {
        println("Flurry - Setting User ID: $id")
    }

    override fun trackEvent(name: String, parameters: Map<String, String>) {
        println("Flurry - Tracking Event: $name")
        println("Flurry - Parameters:")
        println(parameters.toString())
    }
}

object BrazeTracker: EventTracker {
    override fun setUserId(id: String) {
        println("Braze - Setting User ID: $id")
    }

    override fun trackEvent(name: String, parameters: Map<String, String>) {
        println("Braze - Tracking Event: $name")
        println("Braze - Parameters:")
        println(parameters.toString())
    }
}