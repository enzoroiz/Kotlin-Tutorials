fun main(args: Array<String>) {
    val duck = AnimalFactory.getAnimal(AnimalFactory.AnimalType.DUCK)
    val tiger = AnimalFactory.getAnimal(AnimalFactory.AnimalType.TIGER) as Tiger

    listOf(duck, tiger).forEach { it.makeSound() }
}

object AnimalFactory {
    fun getAnimal(type: AnimalType): Animal {
        return when (type) {
            AnimalType.DUCK -> Duck()
            AnimalType.TIGER -> Tiger()
        }
    }

    enum class AnimalType {
        DUCK,
        TIGER
    }
}

interface Animal {
    fun makeSound()
}

class Tiger: Animal {
    override fun makeSound() {
        println("RAWR!")
    }
}

class Duck: Animal {
    override fun makeSound() {
        println("QUACK QUACK")
    }
}