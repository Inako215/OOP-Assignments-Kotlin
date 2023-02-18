package partsStore

class Computer(
    partCategory: String,
    name: String,
    retailPrice: Double,
    cost: Double,
    quantityStock: Int,
    quantitySold: Int,
    detailDescription: String,
    private var gbRam: Int,
    private var hdCapacity: Int,
    private var processorSpeed: String
) :
    Parts(partCategory, name, retailPrice, cost, quantityStock, quantitySold, detailDescription) {

    override fun toString(cols: Array<Int>): String {
        return super.toString(cols) + " ${gbRam.toString().padEnd(cols[7])} ${
            hdCapacity.toString().padEnd(cols[8])
        } ${processorSpeed.padEnd(cols[9])}"
    }

    override fun toTab(): String {
        return super.toTab() + "$gbRam\t$hdCapacity\t$processorSpeed"
    }

    override fun showDetails(): String {
        var details: String = super.showDetails()
        details += """
            Ram:                     $gbRam
            Hard Drive Capacity:     $hdCapacity
            Processor Speed:         $processorSpeed 
        """.trimIndent()
        return details + "\n" + "-".repeat(50)
    }
}