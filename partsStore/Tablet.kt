package partsStore

class Tablet(
    partCategory: String,
    name: String,
    retailPrice: Double,
    cost: Double,
    quantityStock: Int,
    quantitySold: Int,
    detailDescription: String,
    private var screenSize: String,
    private var mbRam: Int,
    private var sdSlot: Boolean
) : Parts(partCategory, name, retailPrice, cost, quantityStock, quantitySold, detailDescription) {

    override fun toString(cols: Array<Int>): String {
        return super.toString(cols) + " ${screenSize.padEnd(cols[7])} ${
            mbRam.toString().padEnd(cols[8])
        } ${sdSlot.toString().padEnd(cols[9])}"
    }

    override fun toTab(): String {
        return super.toTab() + "$screenSize\t$mbRam\t$sdSlot"
    }

    override fun showDetails(): String {
        var details: String = super.showDetails()
        details += """
            Screen Size:             $screenSize
            MB of RAM:               $mbRam
            SD CardSlot:             $sdSlot 
        """.trimIndent()
        return details + "\n" + "-".repeat(50)
    }
}