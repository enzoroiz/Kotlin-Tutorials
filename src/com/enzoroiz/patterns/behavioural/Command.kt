package com.enzoroiz.patterns.behavioural

import java.util.*

fun main(args: Array<String>) {
    val map = mutableListOf(
        mutableListOf('-', '-', '-', '-', 'x'),
        mutableListOf('-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-'),
        mutableListOf('-', '-', '-', '-', '-')
    )

    val drivingHandler = DrivingHandler(StreetMap(map))
    val drivingInstructions = listOf(
        GoDown(drivingHandler),
        GoDown(drivingHandler),
        GoDown(drivingHandler),
        GoDown(drivingHandler),
        GoUp(drivingHandler),
        GoUp(drivingHandler),
        GoUp(drivingHandler),
        GoUp(drivingHandler),
        GoRight(drivingHandler),
        GoRight(drivingHandler),
        GoRight(drivingHandler),
        GoRight(drivingHandler),
        GoLeft(drivingHandler),
        GoLeft(drivingHandler),
        GoLeft(drivingHandler),
        GoLeft(drivingHandler)
    )

    Driver().apply {
        addDrivingDirections(drivingInstructions)
        driveToNext()
        goBackToPrevious()
        driveToEnd()
    }
}

// Command Pattern wraps a request into an object that knows everything needed for an specific action to be performed
// Allows one to separate who requests an action to be performed to who's actually performing it
// The Command (DrivingInstruction) exposes execute() method also undo() if the case
// Concrete Commands (GoUp / GoDown...) uses the receiver to achieve its tasks
// The Receiver (DrivingHandler) is the one who actually implements the task execution
// The Invoker is the class that actually uses the command to make the requests (Driver)

data class StreetMap(
    val details: MutableList<MutableList<Char>>,
    var position: Pair<Int, Int> = Pair(0, 0)
)

interface DrivingInstruction {
    fun execute(): Pair<Int, Int>
    fun undo(): Pair<Int, Int>
}

class Driver {
    private val drivingInstructions = LinkedList<DrivingInstruction>()
    private var lastDrivingInstruction: DrivingInstruction? = null

    fun addDrivingDirections(drivingInstructions: List<DrivingInstruction>) {
        this.drivingInstructions.addAll(drivingInstructions)
    }

    fun driveToNext() {
        lastDrivingInstruction = drivingInstructions.poll()
        lastDrivingInstruction ?. let {
            println("Getting next driving instruction.")
            val position = it.execute()
            println("Current Position: (${position.first}, ${position.second})")
        } ?: println("There aren't any driving instructions left.")
    }

    fun goBackToPrevious() {
        lastDrivingInstruction?.undo()?.let { position ->
            println("Undoing last driving instruction.")
            println("Current Position: (${position.first}, ${position.second})")
        }
    }

    fun driveToEnd() {
        for (i in 0..drivingInstructions.size) {
            driveToNext()
        }
    }
}

class DrivingHandler(private val streetMap: StreetMap) {
    var currentPosition = streetMap.position
        private set

    fun goUp(): Pair<Int, Int> {
        var upwardPosition = streetMap.position.first - 1
        if (upwardPosition < 0) {
            println("You're on the edge. You can't drive up.")
            upwardPosition = streetMap.position.first
        } else if (streetMap.details[upwardPosition][streetMap.position.second] == 'x') {
            println("There's an obstacle here. You can't drive up.")
            upwardPosition = streetMap.position.first
        }

        currentPosition = Pair(upwardPosition, streetMap.position.second)
        streetMap.position = currentPosition
        return currentPosition
    }

    fun goDown(): Pair<Int, Int> {
        var downwardPosition = streetMap.position.first + 1
        if (downwardPosition == streetMap.details.size) {
            println("You're on the edge. You can't drive down.")
            downwardPosition = streetMap.position.first
        } else if (streetMap.details[downwardPosition][streetMap.position.second] == 'x') {
            println("There's an obstacle here. You can't drive down.")
            downwardPosition = streetMap.position.first
        }

        currentPosition = Pair(downwardPosition, streetMap.position.second)
        streetMap.position = currentPosition
        return currentPosition
    }

    fun goRight(): Pair<Int, Int> {
        var rightwardPosition = streetMap.position.second + 1
        if (rightwardPosition == streetMap.details[streetMap.position.first].size) {
            println("You're on the edge. You can't drive right.")
            rightwardPosition = streetMap.position.second
        } else if (streetMap.details[streetMap.position.first][rightwardPosition] == 'x') {
            println("There's an obstacle here. You can't drive right.")
            rightwardPosition = streetMap.position.second
        }

        currentPosition = Pair(streetMap.position.first, rightwardPosition)
        streetMap.position = currentPosition
        return currentPosition
    }

    fun goLeft(): Pair<Int, Int> {
        var leftwardPosition = streetMap.position.second - 1
        if (leftwardPosition < 0) {
            println("You're on the edge. You can't drive left.")
            leftwardPosition = streetMap.position.second
        } else if (streetMap.details[streetMap.position.first][leftwardPosition] == 'x') {
            println("There's an obstacle here. You can't drive left.")
            leftwardPosition = streetMap.position.second
        }

        currentPosition = Pair(streetMap.position.first, leftwardPosition)
        streetMap.position = currentPosition
        return currentPosition
    }

    fun goToPosition(positionToGo: Pair<Int, Int>?): Pair<Int, Int> {
        positionToGo?.let {
            currentPosition = it
            streetMap.position = currentPosition
        }

        return currentPosition
    }
}

class GoUp(private val drivingHandler: DrivingHandler): DrivingInstruction {
    private var position: Pair<Int, Int>? = null

    override fun execute(): Pair<Int, Int> {
        position = drivingHandler.currentPosition.copy()
        return drivingHandler.goUp()
    }

    override fun undo(): Pair<Int, Int> {
        return drivingHandler.goToPosition(position)
    }
}

class GoDown(private val drivingHandler: DrivingHandler): DrivingInstruction {
    private var position: Pair<Int, Int>? = null

    override fun execute(): Pair<Int, Int> {
        position = drivingHandler.currentPosition.copy()
        return drivingHandler.goDown()
    }

    override fun undo(): Pair<Int, Int> {
        return drivingHandler.goToPosition(position)
    }
}

class GoRight(private val drivingHandler: DrivingHandler): DrivingInstruction {
    private var position: Pair<Int, Int>? = null

    override fun execute(): Pair<Int, Int> {
        position = drivingHandler.currentPosition.copy()
        return drivingHandler.goRight()
    }

    override fun undo(): Pair<Int, Int> {
        return drivingHandler.goToPosition(position)
    }
}

class GoLeft(private val drivingHandler: DrivingHandler): DrivingInstruction {
    private var position: Pair<Int, Int>? = null

    override fun execute(): Pair<Int, Int> {
        position = drivingHandler.currentPosition.copy()
        return drivingHandler.goLeft()
    }

    override fun undo(): Pair<Int, Int> {
        return drivingHandler.goToPosition(position)
    }
}