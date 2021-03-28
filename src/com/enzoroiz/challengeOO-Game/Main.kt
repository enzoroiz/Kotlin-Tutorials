import LootType.POTION

fun main(args: Array<String>) {
    println("Hello World")
    var enzo = Player("Enzo", 3, 1)
    var roiz = Player("Roiz", 5, 3, 10)

    var sword = Weapon("Sword", 10)
    var axe = Weapon("Axe", 25)

    enzo.weapon = sword
    roiz.weapon = axe
    enzo.show()
    roiz.show()

    roiz.weapon = sword
    enzo.weapon = roiz.weapon

    var redPotion = Loot("Red Potion", POTION, 4.5)
    println(redPotion)
    if (enzo.dropLoot(redPotion)) println("Red Potion dropped")
    else println("You don't have a red potion")
    enzo.getLoot(redPotion)

    enzo.show()
    roiz.show()

    var uglyTroll = Troll("uglyTroll")
    println(uglyTroll)
    uglyTroll.takeDamage(40)
    println(uglyTroll)
    uglyTroll.takeDamage(20)
}
