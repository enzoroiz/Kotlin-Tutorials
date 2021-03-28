open class Enemy (val name: String, var hitPoints: Int, var lives: Int) {
    open fun takeDamage(damage: Int) {
        var remainingHitPoints = hitPoints - damage
        if (remainingHitPoints > 0) {
            hitPoints = remainingHitPoints
            println("$name took $damage point of damage and has $hitPoints remaining hit points")
        } else {
            if (lives > 0) {
                lives -= 1
                println("Lost a life")
            }
            else println("No lives left, $name is dead")
        }
    }

    override fun toString(): String {
        return "Name: $name / Hit Points: $hitPoints / Lives: $lives"
    }
}