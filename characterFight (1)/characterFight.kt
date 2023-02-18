package CharacterFight

import java.io.File

/************************************************************
Name:         Eliana Henderson
Date:         10/30/2022
Assignment:   Character Fight
Class Number: 283
Description:  a game that will allow 2 different fictional characters to face off against each other in a simulated environment.
 ************************************************************/

class Menu(var menuItems: Array<String>, var prompt: String) {

    var quit = menuItems.size
    fun displayMenu(): Int {
        for ((index, item) in menuItems.withIndex()) {
            println("${index + 1}. $item")
        }
        return promptForInt(prompt, 1..menuItems.size)
    }
}

class Character(
    var name: String,
    var race: String,
    var hitPoints: Int,
    var strength: Int,
    var agility: Int,
    var weapon: Weapons,
    var armor: Armor
) {

    var currentHitPoints = hitPoints

    init {
        var minAgility = 1
        var maxAgility = 10
        var minStrength = 1
        var maxStrength = 50
        var minHitPoint = 1
        var maxHitPoints = 100

        if (agility < minAgility) {
            agility = minAgility
        }
        if (agility > maxAgility) {
            agility = maxAgility
        }
        if (hitPoints < minHitPoint) {
            hitPoints = minHitPoint
        }
        if (hitPoints > maxHitPoints) {
            hitPoints = maxHitPoints
        }
        if (strength < minStrength) {
            strength = minStrength
        }
        if (strength > maxStrength) {
            strength = maxStrength
        }

    }

    override fun toString(): String {
        return """Name: $name
            |Race: $race
            |Hit Points: $hitPoints
            |Strength: $strength
            |Agility: $agility
            |Weapon: $weapon
            |Armor: $armor
        """.trimMargin()
    }

    fun currentStatus() : String {
        return  """$name has $currentHitPoints hit points out of $hitPoints remaining"""
    }

    fun showHitsReduced(hits: Int, armorSave: Int) :String {
        var damageReduced = (hits - armorSave)

        if (armorSave >= hits) {
            damageReduced = 0
        }

        return """$name's HP is reduced by $damageReduced"""
    }

    fun reviveCharacter() {
        currentHitPoints = hitPoints
    }

    fun reduceHits(hits: Int, armorSave: Int) {
        if ((hits - armorSave) > currentHitPoints) {
            currentHitPoints = 0
        } else {
            if (armorSave >= hits) {
                currentHitPoints = currentHitPoints
            } else {
                currentHitPoints -= (hits - armorSave)
            }
        }
    }

}

class Dice(private var numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }

}

open class Item(var name: String)

class Weapons(name: String, var damageHits: Int) :
    Item(name) {
    override fun toString(): String {
        return """$name
            |Hits: $damageHits
        """.trimMargin()
    }
}

class Armor(name: String, var protectionHits: Int) :
    Item(name) {
    override fun toString(): String {
        return """$name
            |Hits: $protectionHits
        """.trimMargin()
    }
}

fun main() {
    var menu = Menu(arrayOf("Load Character 1", "Load Character 2", "Fight", "Quit"), "Please select an option: ")
    var character1 = loadCharacter("legolas.txt")
    var character2 = loadCharacter("gimli.txt")


    do {
        var userChoice = menu.displayMenu()
        when (userChoice) {
            1 -> character1 = loadCharacter("")
            2 -> character2 = loadCharacter("")
            3 -> fight(character1, character2)
        }

    } while (menu.quit != userChoice)
}

fun promptForInt(prompt: String, intRange: IntRange = Int.MIN_VALUE..Int.MAX_VALUE): Int {
    var input = ""
    var firstTime = true
    val isInt = """\A-?\d+\Z""".toRegex()
    do {
        if (!firstTime) {
            println("Invalid number - must be a whole number between ${intRange.first} and ${intRange.last}")
        }
        firstTime = false
        print(prompt)
        input = readln()!!
    } while (input.isEmpty() || !isInt.matches(input) || input.toInt() !in intRange)
    return input.toInt()
}

fun promptForString(prompt: String): String {
    var input = ""
    do {
        print(prompt)
        input = readln()!!
    } while (input.isEmpty())
    return input
}

fun loadCharacter(file: String): Character {
    if (file.isEmpty()) {
        var file = promptForString("Please enter a character file: ")
        var lines = File("src/main/kotlin/$file").readLines()
        var wepLine = lines[1].split(",")
        var armorLine = lines[2].split(",")
        var person = lines[0].split(",")

        var weapon = Weapons(wepLine[0], wepLine[1].trim().toInt())
        var armor = Armor(armorLine[0], armorLine[1].trim().toInt())
        return Character(
            person[0],
            person[1],
            person[2].trim().toInt(),
            person[3].trim().toInt(),
            person[4].trim().toInt(),
            weapon,
            armor
        )
    } else {
        var lines = File("src/main/kotlin/$file").readLines()
        var wepLine = lines[1].split(",")
        var armorLine = lines[2].split(",")
        var person = lines[0].split(",")

        var weapon = Weapons(wepLine[0], wepLine[1].trim().toInt())
        var armor = Armor(armorLine[0], armorLine[1].trim().toInt())
        return Character(
            person[0],
            person[1],
            person[2].trim().toInt(),
            person[3].trim().toInt(),
            person[4].trim().toInt(),
            weapon,
            armor
        )
    }
}

fun fight(character1: Character, character2: Character) {

    var d4 = Dice(4)
    var d8 = Dice(8)
    var d10 = Dice(10)
    var d15 = Dice(15)
    var char1DAgile = Dice(character1.agility)
    var char2DAgile = Dice(character2.agility)
    var dead = false

    character1.reviveCharacter()
    character2.reviveCharacter()

    while (!dead) {

        var char1Hit = (character1.strength * (1.0 / d4.roll()) + (character1.weapon.damageHits / d8.roll())).toInt()
        var char1ArmorSave = (character1.armor.protectionHits / d15.roll())
        var char2Hit = (character2.strength * (1.0 / d4.roll()) + (character2.weapon.damageHits / d8.roll())).toInt()
        var char2ArmorSave = (character2.armor.protectionHits / d15.roll())

        char1DAgile.roll()
        char2DAgile.roll()

        println()

        if (char1DAgile.roll() >= char2DAgile.roll()) {
            d10.roll()
            if (d10.roll() < character1.agility) {
                println("${character1.name} fights with the ${character1.weapon.name}")
                character2.reduceHits(char1Hit, char2ArmorSave)
                println("${character1.name} hit ${character2.name} for $char1Hit Damage, ${character2.name}'s armor saved for $char2ArmorSave")
                println(character2.showHitsReduced(char1Hit, char2ArmorSave))
            } else {
                println("${character1.name} fights with the ${character1.weapon.name} and Missed!")
            }

            if (character1.currentHitPoints > 0 && character2.currentHitPoints > 0) {
                d10.roll()
                if (d10.roll() < character2.agility) {
                    println("${character2.name} fights with the ${character2.weapon.name}")
                    character1.reduceHits(char2Hit, char1ArmorSave)
                    println("${character2.name} hit ${character1.name} for $char2Hit Damage, ${character1.name}'s armor saved for $char1ArmorSave")
                    println(character1.showHitsReduced(char2Hit, char1ArmorSave))
                } else {
                    println("${character2.name} fights with the ${character2.weapon.name} and Missed!")
                }
            }

        } else {
            d10.roll()
            if (d10.roll() < character2.agility) {
                println("${character2.name} fights with the ${character2.weapon.name}")
                character1.reduceHits(char2Hit, char1ArmorSave)
                println("${character2.name} hit ${character1.name} for $char2Hit Damage, ${character1.name}'s armor saved for $char1ArmorSave")
                println(character1.showHitsReduced(char2Hit, char1ArmorSave))
            } else {
                println("${character2.name} fights with the ${character2.weapon.name} and Missed!")
            }
            if (character1.currentHitPoints > 0 && character2.currentHitPoints > 0) {
                d10.roll()
                if (d10.roll() < character1.agility) {
                    println("${character1.name} fights with the ${character1.weapon.name}")
                    character2.reduceHits(char1Hit, char2ArmorSave)
                    println("${character1.name} hit ${character2.name} for $char1Hit Damage, ${character2.name}'s armor saved for $char2ArmorSave")
                    println(character2.showHitsReduced(char1Hit, char2ArmorSave))
                } else {
                    println("${character1.name} fights with the ${character1.weapon.name} and Missed!")
                }
            }
        }

        println()
        println("""
            =================================================
            ${character1.currentStatus()}
            ${character2.currentStatus()}
            =================================================
        """.trimIndent())
        println()
        print("Press enter to continue: ")
        readln()
        if (character1.currentHitPoints <= 0) {
            dead = true
            println()
            println("""
            ${character2.name} WINS!
            =================================================
            ${character1.currentStatus()}
            ${character2.currentStatus()}
            =================================================
        """.trimIndent())
            println()
        }
        if (character2.currentHitPoints <= 0) {
            dead = true
            println()
            println("""
            ${character1.name} WINS!
            =================================================
            ${character1.currentStatus()}
            ${character2.currentStatus()}
            =================================================
        """.trimIndent())
            println()
        }
    }
}
