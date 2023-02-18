package partsStore

class Printer(
    partCategory: String,
    name: String,
    retailPrice: Double,
    cost: Double,
    quantityStock: Int,
    quantitySold: Int,
    detailDescription: String,
    private var color: Boolean,
    private var pagesPerMin: Int,
    private var scanner: Boolean
) : Parts(partCategory, name, retailPrice, cost, quantityStock, quantitySold, detailDescription) {
    override fun toString(cols: Array<Int>): String {
        return super.toString(cols) + " ${color.toString().padEnd(cols[7])} ${
            pagesPerMin.toString().padEnd(cols[8])
        } ${scanner.toString().padEnd(cols[9])}"
    }

    override fun toTab(): String {
        return super.toTab() + "$color\t$pagesPerMin\t$scanner"
    }

    override fun showDetails(): String {
        var details: String = super.showDetails()
        details += """
            Color:                   $color
            Pages Per Minute:        $pagesPerMin
            Scanner:                 $scanner 
        """.trimIndent()
        return details + "\n" + "-".repeat(50)
    }
}