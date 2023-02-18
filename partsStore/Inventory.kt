package partsStore

import java.io.File

class Inventory(fileName: String) {
    private var parts = mutableListOf<Parts>()
    private var cols = arrayOf<Int>() //TODO: Turn into a hashmap?
    private var header = arrayOf<String>()

    //init{
//   var lines = File(file).readLines()
//
//    var cols = lines[0].split("\t")
//    var titles = lines[1].split("\t")
//
//    var header = hashMapOf<String, Int>()
//
//    for(col in cols) {
//        for(title in titles){
//            header[title] = col.toInt()
//        }
//    }
    init {
//    var lineNum = 0
//    var tempWidths = listOf<String>()
//    var tempTitles = listOf<String>()
//    var widthTitles = hashMapOf<String, Int>()
//
//    File(fileName).forEachLine {
//        if( lineNum == 0 ) { // processing the widths
//            tempWidths = it.split("\t")
//        } else if( lineNum == 1 ){  // processing the titles
//            tempTitles = it.split("\t")
//        } else {
//            // dealing with each record
//        }
//
//
//        lineNum++
//    }
//
//    // combine the widths and the titles into a hashmap
//    for( i in tempWidths.indices ){
//        widthTitles[ tempTitles[i] ] = tempWidths[i].toInt()
//    }

        File(fileName).forEachLine {

            val pieces = it.split("\t")

            if (pieces[0] == "Part_Category") {
                header += pieces[0]
                header += pieces[1]
                header += pieces[2]
                header += pieces[3]
                header += pieces[4]
                header += pieces[5]
                header += pieces[6]
                header += pieces[7]
                header += pieces[8]
                header += pieces[9]
            }

            if (pieces[0] == "15") {
                cols += pieces[0].toInt()
                cols += pieces[1].toInt()
                cols += pieces[2].toInt()
                cols += pieces[3].toInt()
                cols += pieces[4].toInt()
                cols += pieces[5].toInt()
                cols += pieces[6].toInt()
                cols += pieces[7].toInt()
                cols += pieces[8].toInt()
                cols += pieces[9].toInt()
            }

            if (pieces[0] == "COMPUTER") {
                val computer = Computer(
                    pieces[0],
                    pieces[1],
                    pieces[2].toDouble(),
                    pieces[3].toDouble(),
                    pieces[4].toInt(),
                    pieces[5].toInt(),
                    pieces[6],
                    pieces[7].toInt(),
                    pieces[8].toInt(),
                    pieces[9]
                )
                parts.add(computer)
            }

            if (pieces[0] == "PRINTER") {
                val printer = Printer(
                    pieces[0],
                    pieces[1],
                    pieces[2].toDouble(),
                    pieces[3].toDouble(),
                    pieces[4].toInt(),
                    pieces[5].toInt(),
                    pieces[6],
                    pieces[7].toBoolean(),
                    pieces[8].toInt(),
                    pieces[9].toBoolean()
                )
                parts.add(printer)
            }

            if (pieces[0] == "TABLET") {
                val tablet = Tablet(
                    pieces[0],
                    pieces[1],
                    pieces[2].toDouble(),
                    pieces[3].toDouble(),
                    pieces[4].toInt(),
                    pieces[5].toInt(),
                    pieces[6],
                    pieces[7],
                    pieces[8].toInt(),
                    pieces[9].toBoolean()
                )
                parts.add(tablet)
            }
        }
    }

    fun showInventory(): String {
        var part = ""
        for (i in 0 until parts.size) {
            part += "${parts[i].toString(cols)} \n"
        }
        return header() + divider() + part
    }

    private fun header(): String {
        return "\n${header[0].replace('_', ' ').padEnd(cols[0])} ${header[1].padEnd(cols[1])} ${
            header[2].replace(
                '_',
                ' '
            ).padEnd(cols[2])
        } ${
            header[3].padEnd(
                cols[3]
            )
        } ${header[4].replace('_', ' ').padEnd(cols[4])} ${
            header[5].replace('_', ' ').padEnd(cols[5])
        } ${header[6].replace('_', ' ').padEnd(cols[6])} ${header[7].padEnd(cols[7])} ${
            header[8].padEnd(
                cols[8]
            )
        } ${header[9].padEnd(cols[9])}"
    }

    private fun grandTotalHeader(): String {
        return "\n${header[0].replace('_', ' ').padEnd(cols[0])} ${header[1].padEnd(cols[1])} ${
            header[2].replace(
                '_',
                ' '
            ).padEnd(cols[2])
        } ${
            header[3].padEnd(
                cols[3]
            )
        } ${header[4].replace('_', ' ').padEnd(cols[4])} ${
            header[5].replace('_', ' ').padEnd(cols[5])
        } ${
            header[6].replace('_', ' ').padEnd(cols[6])
        }" + " Profit Each".padStart(cols[2] + 1) + "Grand Total".padStart(cols[2] + 1)
    }

    private fun divider(): String {
        return "\n" + "-".repeat(cols[0]) + " " + "-".repeat(cols[1]) + " " + "-".repeat(cols[2]) + " " + "-".repeat(
            cols[3]
        ) + " " + "-".repeat(cols[4]) + " " + "-".repeat(cols[5]) + " " + "-".repeat(cols[6]) + " " + "-".repeat(cols[7]) + " " + "-".repeat(
            cols[8]
        ) + " " + "-".repeat(cols[9]) + "\n"
    }

    private fun grandTotalDivider(): String {
        return "\n" + "-".repeat(cols[0]) + " " + "-".repeat(cols[1]) + " " + "-".repeat(cols[2]) + " " + "-".repeat(
            cols[3]
        ) + " " + "-".repeat(cols[4]) + " " + "-".repeat(cols[5]) + " " + "-".repeat(cols[6]) + " " + "-".repeat(cols[2]) + " " + "-".repeat(
            cols[2]
        ) + "\n"
    }

    fun showCategory(userChoice: Int): String {
        var category = ""
        for (i in parts) {
            when (userChoice) {
                1 -> {
                    if (i.partCategory == "COMPUTER") {
                        category += "${i.toString(cols)} \n"
                    }
                }

                2 -> {
                    if (i.partCategory == "TABLET") {
                        category += "${i.toString(cols)} \n"
                    }
                }

                3 -> {
                    if (i.partCategory == "PRINTER") {
                        category += "${i.toString(cols)} \n"
                    }
                }
            }
        }
        return header() + divider() + category
    }

    fun sellPart(choice: Int) {
        val part: Parts = parts[choice - 1]
        part.quantityStock--
        part.quantitySold++
    }

    fun increaseInventory(userChoice: Int, increaseBy: Int) {
        val part: Parts = parts[userChoice - 1]
        part.quantityStock += increaseBy
    }

    fun updatePart(userChoice: Int) {
        val part: Parts = parts[userChoice - 1]
        if (part.partCategory == "COMPUTER") {
            val name = promptForString("Please enter new part Name: ")
            val retailPrice = promptForDouble("Please enter new Retail Price: ")
            val cost = promptForDouble("Please enter new Cost: ")
            val qtyStock = promptForInt("Please enter new QTY In Stock: ")
            val qtySold = promptForInt("Please enter new QTY Sold: ")
            val description = promptForString("Please enter new Detailed Description: ")
            val ram = promptForInt("Please enter new Ram in GB: ")
            val hdCapacity = promptForInt("Please enter new Hard Drive Capacity: ")
            val processorSpeed = promptForString("Please enter new Processor Speed (Ghz): ")
            parts[userChoice - 1] = (Computer(
                part.partCategory,
                name,
                retailPrice,
                cost,
                qtyStock,
                qtySold,
                description,
                ram,
                hdCapacity,
                processorSpeed
            ))
        }

        if (part.partCategory == "TABLET") {
            val name = promptForString("Please enter new part Name: ")
            val retailPrice = promptForDouble("Please enter new Retail Price: ")
            val cost = promptForDouble("Please enter new Cost: ")
            val qtyStock = promptForInt("Please enter new QTY In Stock: ")
            val qtySold = promptForInt("Please enter new QTY Sold: ")
            val description = promptForString("Please enter new Detailed Description: ")
            val screenSize = promptForString("Please enter new Screen Size: ")
            val ram = promptForInt("Please enter new Ram in MB: ")
            val sdSlot = promptForString("Does it have an SD Slot (True/False): ").toBoolean()
            parts[userChoice - 1] = (Tablet(
                part.partCategory,
                name,
                retailPrice,
                cost,
                qtyStock,
                qtySold,
                description,
                screenSize,
                ram,
                sdSlot
            ))
        }

        if (part.partCategory == "PRINTER") {
            val name = promptForString("Please enter new part Name: ")
            val retailPrice = promptForDouble("Please enter new Retail Price: ")
            val cost = promptForDouble("Please enter new Cost: ")
            val qtyStock = promptForInt("Please enter new QTY In Stock: ")
            val qtySold = promptForInt("Please enter new QTY Sold: ")
            val description = promptForString("Please enter new Detailed Description: ")
            val color = promptForString("Does it have Color (True/False): ").toBoolean()
            val pagePerMin = promptForInt("Please enter new Pages Printer Per Minute: ")
            val scanner = promptForString("Does it have a Scanner (True/False): ").toBoolean()
            parts[userChoice - 1] = (Printer(
                part.partCategory,
                name,
                retailPrice,
                cost,
                qtyStock,
                qtySold,
                description,
                color,
                pagePerMin,
                scanner
            ))
        }

    }

    fun addPart(userChoice: Int) {
        val part: Parts = parts[userChoice]
        when (userChoice) {
            1 -> {
                val name = promptForString("Please enter new part Name: ")
                val retailPrice = promptForDouble("Please enter new Retail Price: ")
                val cost = promptForDouble("Please enter new Cost: ")
                val qtyStock = promptForInt("Please enter new QTY In Stock: ")
                val qtySold = promptForInt("Please enter new QTY Sold: ")
                val description = promptForString("Please enter new Detailed Description: ")
                val ram = promptForInt("Please enter new Ram in GB: ")
                val hdCapacity = promptForInt("Please enter new Hard Drive Capacity: ")
                val processorSpeed = promptForString("Please enter new Processor Speed (Ghz): ")
                parts.add(
                    Computer(
                        part.partCategory,
                        name,
                        retailPrice,
                        cost,
                        qtyStock,
                        qtySold,
                        description,
                        ram,
                        hdCapacity,
                        processorSpeed
                    )
                )
            }

            2 -> {
                val name = promptForString("Please enter new part Name: ")
                val retailPrice = promptForDouble("Please enter new Retail Price: ")
                val cost = promptForDouble("Please enter new Cost: ")
                val qtyStock = promptForInt("Please enter new QTY In Stock: ")
                val qtySold = promptForInt("Please enter new QTY Sold: ")
                val description = promptForString("Please enter new Detailed Description: ")
                val screenSize = promptForString("Please enter new Screen Size: ")
                val ram = promptForInt("Please enter new Ram in MB: ")
                val sdSlot = promptForString("Does it have an SD Slot (True/False): ").toBoolean()
                parts.add(
                    Tablet(
                        part.partCategory,
                        name,
                        retailPrice,
                        cost,
                        qtyStock,
                        qtySold,
                        description,
                        screenSize,
                        ram,
                        sdSlot
                    )
                )
            }

            3 -> {
                val name = promptForString("Please enter new part Name: ")
                val retailPrice = promptForDouble("Please enter new Retail Price: ")
                val cost = promptForDouble("Please enter new Cost: ")
                val qtyStock = promptForInt("Please enter new QTY In Stock: ")
                val qtySold = promptForInt("Please enter new QTY Sold: ")
                val description = promptForString("Please enter new Detailed Description: ")
                val color = promptForString("Does it have Color (True/False): ").toBoolean()
                val pagePerMin = promptForInt("Please enter new Pages Printer Per Minute: ")
                val scanner = promptForString("Does it have a Scanner (True/False): ").toBoolean()
                parts.add(
                    Printer(
                        part.partCategory,
                        name,
                        retailPrice,
                        cost,
                        qtyStock,
                        qtySold,
                        description,
                        color,
                        pagePerMin,
                        scanner
                    )
                )
            }
        }
    }

    fun removePart(userChoice: Int) {
        parts.removeAt(userChoice - 1)
    }

    fun showPartDetail(userChoice: Int): String {
        val part: Parts = parts[userChoice - 1]

        return part.showDetails()
    }

    fun showGrandTotals(): String {
        var part = ""
        var grandTotal = 0.0
        for (i in 0 until parts.size) {
            part += "${parts[i].showGrandTotal(cols)} \n"
            grandTotal += ((parts[i].retailPrice - parts[i].cost) * parts[i].quantitySold)
        }
        return grandTotalHeader() + grandTotalDivider() + part + "-".repeat(15)
            .padStart(173) + "\n${grandTotal.toDollar().padStart(173)}"
    }

    fun catMenu(options: Array<String>): String {
        var menu = ""
        for ((index, option) in options.withIndex()) {
            menu += "${index + 1}. $option \n"
        }
        return menu
    }

    fun partMenu(): String {
        var menu = ""
        for ((index, part) in parts.withIndex()) {
            menu += "${index + 1}. ${part.toString(cols)} \n"
        }
        return header() + divider() + menu
    }

    fun partNameMenu(): String {
        var name = ""
        for ((index, part) in parts.withIndex()) {
            name += "${index + 1}. ${part.name} \n"
        }
        return name
    }

    fun toTab(): String {
        var lines = ""
        for (i in header) {
            lines += "${i}\t"
        }
        lines = lines.dropLast(1)
        lines += "\n"
        for (i in cols) {
            lines += "${i}\t"
        }
        return lines.dropLast(1)
    }

    fun saveFile(): String {
        var tabbed = ""
        for (part in parts) {
            tabbed += part.toTab()
            tabbed += "\n"
        }
        return tabbed
    }
}