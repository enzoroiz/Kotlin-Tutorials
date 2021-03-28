class Weapon (val name: String, val damageInflicted: Int) {
    override fun toString(): String {
        return "Weapon: $name / $damageInflicted damage"
    }
}