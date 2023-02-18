package partsStore

class Menu(private var menuItems: Array<String>, private var prompt: String) {

    var quit = menuItems.size
    fun displayMenu(): Int {
        for ((index, item) in menuItems.withIndex()) {
            println("${index + 1}. $item")
        }
        return promptForInt(prompt, 1..menuItems.size)
    }
}