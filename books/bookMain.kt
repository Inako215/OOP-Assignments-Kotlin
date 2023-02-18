import java.io.File
import java.util.*

/************************************************************
Name:         Eliana Henderson
Date:         10/06/2022
Assignment:   Books Class
Class Number: 283
Description:  An application to manage a library of books.
 ************************************************************/

class Book(var title: String, var author: String, var publicationYear: Int, var numberOfPages: Int, var isbn: String) {
    override fun toString(): String {
        return """${title.take(30).padEnd(30)} ${
            author.take(15).padEnd(15)
        } ${publicationYear.toString().padStart(6)} ${
            numberOfPages.toString().padStart(5)
        } ${isbn.padEnd(13)} https://www.biblio.com/${isbn}"""
    }

    fun toTab(): String {
        //Function to add a tab delimiter when writing back to the text file
        return "$title\t$author\t$publicationYear\t$numberOfPages\t$isbn"
    }
}

fun main() {
    val books = mutableListOf<Book>()
    val fileName = "src/main/kotlin/books.txt"
    File(fileName).forEachLine {
        val (title, author, pubYear, numPages, isbn) = it.split("\t")
        books.add(Book(title, author, pubYear.toInt(), numPages.toInt(), isbn))
    }

    val menuChoice = arrayOf(
        "View all Books",
        "Add book",
        "Update Book",
        "Delete book",
        "View book with most pages",
        "View book with least pages",
        "View books with pages greater than or equal to 200",
        "View books with pages less than 200",
        "View books with pages between 50-300 inclusive",
        "Exit"
    )

    val quit = menuChoice.size
    var userChoice = 0
    while (userChoice != quit) {
        userChoice = menu(menuChoice, "\nPlease enter a selection: ")
        when (userChoice) {
            //View All Books (Function)
            1 -> viewAllBooks(books)
            //Add Book (Function)
            2 -> addBook(books)
            //Update Book (Function)
            3 -> updateBook(books)
            //Delete Book (Function)
            4 -> deleteBook(books)
            //View Book with most pages (Function)
            5 -> viewMostPages(books)
            //View Book with least pages (Function)
            6 -> viewLeastPages(books)
            //View books with pages greater than or equal to 200 (Function)
            7 -> viewGreaterThan200(books)
            //View books with pages less than 200 (Function)
            8 -> viewLessThan200(books)
            //View books with pages between 50-300 inclusive (Function)
            9 -> viewBetween50and300(books)
            //Exit (Write changes to file)
        }
    }
    File(fileName).printWriter().use { out ->
        print("Saving Changes.")
        for (book in books) {
            out.println(book.toTab())
        }
    }
}

fun menu(menuChoice: Array<String>, prompt: String): Int {
    println()
    var userChoice = 0
    for ((index, choice) in menuChoice.withIndex()) {
        println("${index + 1}. $choice")
    }
    do {
        userChoice = promptForInt(prompt, 1..menuChoice.size)
    } while (userChoice !in 1..menuChoice.size + 1)

    return userChoice
}

fun reportHeader() {
    println()
    println(
        """Geeks Publishing, Inc.
Name                           Author          Pub Yr Pages ISBN          URL
------------------------------ --------------- ------ ----- ------------- -------"""
    )
}

fun viewAllBooks(books: MutableList<Book>) {
    reportHeader()
    for (book in books) {
        println(book)
    }
}

fun addBook(books: MutableList<Book>) {
    val date = Calendar.getInstance()
    val title = promptForString("Please enter the book title: ")
    val author = promptForString("Please enter the author of the book: ")
    val publicationYear = promptForInt("Please enter the year the book was published: ", 1601..date.get(Calendar.YEAR))
    val numberOfPages = promptForInt("Please enter the number of pages in the book: ", 1..Int.MAX_VALUE)
    val isbn = promptForString("Please enter the ISBN of the book: ")
    books.add(Book(title, author, publicationYear, numberOfPages, isbn))
}

fun updateBook(books: MutableList<Book>) {
    for ((index, book) in books.withIndex()) {
        println("${index + 1}. ${book.title}")
    }
    println()
    val bookChoice = promptForInt("Please select the book you would like to update: ", 1 until books.size + 1)
    val date = Calendar.getInstance()
    val title = promptForString("Please enter the book title: ")
    val author = promptForString("Please enter the author of the book: ")
    val publicationYear = promptForInt("Please enter the year the book was published: ", 1601..date.get(Calendar.YEAR))
    val numberOfPages = promptForInt("Please enter the number of pages in the book: ", 1..Int.MAX_VALUE)
    val isbn = promptForString("Please enter the ISBN of the book: ")
    books[bookChoice - 1] = (Book(title, author, publicationYear, numberOfPages, isbn))
}

fun deleteBook(books: MutableList<Book>) {
    println()
    for ((index, book) in books.withIndex()) {
        println("${index + 1}. ${book.title}")
    }
    println()
    val bookChoice = promptForInt("Please select the book you would like to delete: ", 1 until books.size + 1)
    books.removeAt(bookChoice - 1)
}

fun viewMostPages(books: MutableList<Book>) {
    var mostPages = Int.MIN_VALUE
    var largestBook = 0

    for ((index, book) in books.withIndex()) {
        if (book.numberOfPages > mostPages) {
            mostPages = book.numberOfPages
            largestBook = index
        }
    }
    reportHeader()
    println(books[largestBook])
}

fun viewLeastPages(books: MutableList<Book>) {
    var leastPages = Int.MAX_VALUE
    var smallestBook = 0

    for ((index, book) in books.withIndex()) {
        if (book.numberOfPages < leastPages) {
            leastPages = book.numberOfPages
            smallestBook = index
        }
    }
    reportHeader()
    println(books[smallestBook])
}

fun viewGreaterThan200(books: MutableList<Book>) {
    reportHeader()
    for (book in books) {
        if (book.numberOfPages >= 200) {
            println(book)
        }
    }
}

fun viewLessThan200(books: MutableList<Book>) {
    reportHeader()
    for (book in books) {
        if (book.numberOfPages < 200) {
            println(book)
        }
    }
}

fun viewBetween50and300(books: MutableList<Book>) {
    reportHeader()
    for (book in books) {
        if (book.numberOfPages in 50..300) {
            println(book)
        }
    }
}

//fun viewBooksWithPageInRange(books: MutableList<Book>, range: IntRange = 1..Int.MAX_VALUE) {
//    val rangeStart = promptForInt("Please enter the least number of pages desired: ")
//    val rangeEnd = promptForInt("Please enter the highest number of pages desired: ")
//
//    reportHeader()
//    for (book in books) {
//        if (book.numberOfPages in rangeStart..rangeEnd) {
//            println(book)
//        }
//    }
//}

fun promptForString(prompt: String): String {
    var input = ""
    do {
        print(prompt)
        input = readln()!!
    } while (input.isEmpty())
    return input
}

fun promptForInt(prompt: String, intRange: IntRange = Int.MIN_VALUE..Int.MAX_VALUE): Int {
    var input = ""
    var firstTime = true
    val isInt = """\D+?|\s+?|\s+?\d+?|\d+?\s+?|(\d+?\.\d+?)|0\d+?|\.+?\d+?|\d+?\.+?""".toRegex()
    do {
        if (!firstTime) {
            println("Invalid number - must be a whole number between ${intRange.first} and ${intRange.last}")
        }
        firstTime = false
        print(prompt)
        input = readln()!!
    } while (input.isEmpty() || input.matches(isInt) || input.toInt() !in intRange)
    return input.toInt()
}