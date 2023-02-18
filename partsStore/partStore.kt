package partsStore

import java.io.File

/************************************************************
Name:         Eliana Henderson
Date:         11/18/2022
Assignment:   Part Store Inventory
Class Number: 283
Description:  A program that implements the basics of a part store inventory system.
This system will allow the owner of the store to manage the data within the store
 ************************************************************/


fun main() {
    val fileName = "src/main/kotlin/partsStore/parts_database.tsv"
    val inv = Inventory(fileName)

    val menu = Menu(
        arrayOf(
            "List All Parts",
            "Show All of a particular Category",
            "Sell a Part",
            "Increase inventory of a particular part",
            "Update elements of a single part",
            "Add a brand new part to inventory",
            "Completely remove a part from inventory",
            "Show the detail information about a part",
            "Show Total Inventory Report",
            "Quit"
        ),
        "\nEnter a selection: "
    )

    do {
        val userChoice = menu.displayMenu()
        when (userChoice) {
            1 -> println(inv.showInventory())
            2 -> showCategory(inv)
            3 -> sellPart(inv)
            4 -> increaseInventory(inv)
            5 -> updatePart(inv)
            6 -> addPart(inv)
            7 -> removePart(inv)
            8 -> showPartDetails(inv)
            9 -> showGrandTotal(inv)
        }
    } while (menu.quit != userChoice)
    File(fileName).printWriter().use { out ->
        out.println(inv.toTab())
        out.println(inv.saveFile())
    } //TODO: Move to Inventory
}

fun sellPart(inv: Inventory) {
    println(inv.partMenu())
    val userChoice = promptForInt("What part would you like to sell?: ")
    println()
    inv.sellPart(userChoice)
}

fun addPart(inv: Inventory) {
    val options = arrayOf("Computer", "Tablet", "Printer")
    println(inv.catMenu(options))
    val userChoice = promptForInt("What category is the New Part?: ", 1..options.size)
    inv.addPart(userChoice)
    println()
}

fun showCategory(inv: Inventory) {
    val options = arrayOf("Computer", "Tablet", "Printer")
    println(inv.catMenu(options))
    val userChoice = promptForInt("What category would you like to display?: ", 1..options.size)
    println()
    println(inv.showCategory(userChoice))
}

fun increaseInventory(inv: Inventory) {
    println(inv.partMenu())
    val userChoice = promptForInt("Which parts inventory would you like to increase?: ")
    val increaseBy = promptForInt("How much will the parts quantity increase?: ", 1..Int.MAX_VALUE)
    println()
    inv.increaseInventory(userChoice, increaseBy)
}

fun updatePart(inv: Inventory) {
    println(inv.partMenu())
    val userChoice = promptForInt("Which part would you like to update?: ")
    inv.updatePart(userChoice)
    println()
}

fun removePart(inv: Inventory) {
    println(inv.partMenu())
    val userChoice = promptForInt("Which part would you like to Remove?: ")
    inv.removePart(userChoice)
    println()
}

fun showPartDetails(inv: Inventory) {
    println(inv.partNameMenu())
    val userChoice = promptForInt("Which items details would you like to view?: ")
    println(inv.showPartDetail(userChoice))
    println()
}

fun showGrandTotal(inv: Inventory) {
    println(inv.showGrandTotals())
    println()
}

fun Number.toDollar(): String {
    return "$%.2f".format(this)
}

//TODO: Move Useful Functions to main

fun promptForDouble(prompt: String): Double {
    var input: String
    var firstTime = true
    val isDouble = """\A-?\d+[.,]\d+\Z""".toRegex()
    do {
        if (!firstTime) {
            println("Invalid number - must be a number with a decimal")
        }
        firstTime = false
        print(prompt)
        input = readln()!!
    } while (input.isEmpty() || !isDouble.matches(input))
    return input.toDouble()
}

fun promptForInt(prompt: String, intRange: IntRange = Int.MIN_VALUE..Int.MAX_VALUE): Int {
    var input: String
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
    var input: String
    do {
        print(prompt)
        input = readln()!!
    } while (input.isEmpty())
    return input
}