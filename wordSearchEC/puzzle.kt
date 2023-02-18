package wordSearchEC

import java.io.File

/************************************************************
Name:         Eliana Henderson
Date:         12/05/2022
Assignment:   Word Search (Final)
Class Number: 283
Description:  A program to generate a word search puzzle and puzzles key using a list of provided words.
 ************************************************************/

fun main() {
    val fileName = "src/main/kotlin/wordSearchEC/words.txt"
    val puz = Puzzle(fileName, 45, 45)
    puz.createPuzzle()
    println(puz.displayPuzzleKey())
    println(puz.displayPuzzle())

    File("src/main/kotlin/wordSearchEC/puzzle.txt").printWriter().use { out ->
        out.println(puz.displayPuzzle())
    }
    File("src/main/kotlin/wordSearchEC/puzzleKey.txt").printWriter().use { out ->
        out.println(puz.displayPuzzleKey())
    }
}

class Puzzle(
    fileName: String,
    private var rows: Int,
    private var cols: Int
) {
    private var words = arrayOf("")
    private var puz = Array(rows) { Array(cols) { '.' } }
    private var completePuz = puz.map { it.copyOf() }

    init {

        File(fileName).forEachLine {
            words += it.replace(" ", "")
        }
        //READ ALL WORDS IN ARRAY

    }

    fun createPuzzle() {
        var dir: String
        var row: Int
        var col: Int
        for (word in words.sortedByDescending { it.length }) {
            do {
                dir = arrayOf("North", "South", "West", "East", "NE", "NW", "SE", "SW").random()
                row = (0 until rows - 1).random()
                col = (0 until cols - 1).random()
            } while (!testPlace(word, row, col, dir, puz))
            placeWord(word, row, col, dir, puz)
        }
        fillPuz()
    }

    private fun fillPuz() {
        val fillCharacters = mutableListOf<Char>()

        for (word in words) {
            for (w in word) {
                fillCharacters += w.uppercaseChar()
            }
        }
        for (row in puz.indices) {
            for (col in puz[rows - 1].indices) {
                if (puz[row][col] == '.') {
                    completePuz[row][col] = fillCharacters[(fillCharacters.indices).random()]
                } else {
                    completePuz[row][col] = puz[row][col]
                }
            }
        }
    }

    private fun testPlace(word: String, row: Int, col: Int, dir: String, puz: Array<Array<Char>>): Boolean {
        var currentCol = col
        var currentRow = row
        var canPlace = true

        for (w in word) {
            when (dir) {
                "South" -> {
                    if (currentRow <= puz[0].lastIndex && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentRow++

                    } else {
                        canPlace = false
                    }
                }

                "North" -> {
                    if (currentRow >= rows - (rows - 1) && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentRow--
                    } else {
                        canPlace = false
                    }

                }

                "East" -> {
                    if (currentCol <= puz[0].lastIndex && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentCol++
                    } else {
                        canPlace = false
                    }
                }

                "West" -> {
                    if (currentCol >= cols - (cols - 1) && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentCol--
                    } else {
                        canPlace = false
                    }
                }

                "NE" -> {
                    if ((currentRow >= rows - (rows - 1) && currentCol <= puz[0].lastIndex) && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentRow--; currentCol++
                    } else {
                        canPlace = false
                    }
                }

                "NW" -> {
                    if ((currentRow >= rows - (rows - 1) && currentCol >= cols - (cols - 1)) && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentRow--; currentCol--
                    } else {
                        canPlace = false
                    }
                }

                "SE" -> {
                    if ((currentRow <= puz[0].lastIndex && currentCol <= puz[0].lastIndex) && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentRow++; currentCol++
                    } else {
                        canPlace = false
                    }
                }

                "SW" -> {
                    if ((currentRow <= puz[0].lastIndex && currentCol >= cols - (cols - 1)) && (puz[currentRow][currentCol] == '.' || puz[currentRow][currentCol] == w)) {
                        currentRow++; currentCol--
                    } else {
                        canPlace = false
                    }
                }
            }
        }
        return canPlace
    }

    private fun placeWord(word: String, row: Int, col: Int, dir: String, puz: Array<Array<Char>>) {
        var currentCol = col
        var currentRow = row
        for (w in word) {
            when (dir) {
                "South" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentRow++
                }

                "North" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentRow--
                }

                "East" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentCol++
                }

                "West" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentCol--
                }

                "NE" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentRow--; currentCol++
                }

                "NW" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentRow--; currentCol--
                }

                "SE" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentRow++; currentCol++
                }

                "SW" -> {
                    puz[currentRow][currentCol] = w.uppercaseChar()
                    currentRow++; currentCol--
                }
            }
        }
    }

    fun displayPuzzleKey(): String {
        var puzKey = ""
        for (row in puz.indices) {
            for (col in 0 until puz[row].size) {
                puzKey += "${puz[row][col]}" + " ".repeat(2)
            }
            puzKey += "\n"
        }
        return puzKey + "\n" + printWordList()
    }

    fun displayPuzzle(): String {
        var puzzleString = ""
        for (row in completePuz.indices) {
            for (col in 0 until completePuz[row].size) {
                puzzleString += "${completePuz[row][col]}" + " ".repeat(2)
            }
            puzzleString += "\n"
        }
        return puzzleString + "\n" + printWordList()
    }

    private fun printWordList(): String {
        var wordList = "List of Words to Find:"

        for (i in words.indices) {
            val word = words[i]
            if (i > 0) {
                wordList += " "
            }
            wordList += word.padEnd(30, ' ')
            if (i % 3 == 0 || i == words.size - 1) {
                wordList += "\n"
            }
        }
        return wordList + "-".repeat(95) + "\n"
    }

}