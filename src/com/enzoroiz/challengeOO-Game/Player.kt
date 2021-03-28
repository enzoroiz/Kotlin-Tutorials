class Player(val name: String, var lives: Int = 3, var level: Int = 1, var score: Int = 0) {
    var weapon = Weapon("Fist", 1)
    private val inventory = ArrayList<Loot>()

    fun show() {
        println(toString())
        println()
    }

    fun getLoot(loot: Loot) {
        inventory.add(loot)
    }

    fun dropLoot(loot: Loot): Boolean {
        return if (inventory.contains(loot)) {
            inventory.remove(loot)
        } else false
    }

    fun dropLoot(name: String): Boolean {
        return inventory.removeIf { it.name == name }
    }

    override fun toString(): String {
        return """
            Name: $name
            Lives: $lives
            Level: $level
            Score: $score
            ${weapon.toString()}
        """.trimIndent()
    }
}