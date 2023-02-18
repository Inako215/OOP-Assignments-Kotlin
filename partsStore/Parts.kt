package partsStore

open class Parts(
    var partCategory: String,
    var name: String,
    var retailPrice: Double,
    var cost: Double,
    open var quantityStock: Int,
    open var quantitySold: Int,
    var detailDescription: String
) {
    open fun toString(cols: Array<Int>): String {
        return "${partCategory.padEnd(cols[0])} ${name.padEnd(cols[1])} ${
            retailPrice.toDollar().padEnd(cols[2])
        } ${cost.toDollar().padEnd(cols[3])} ${
            quantityStock.toString().padEnd(cols[4])
        } ${quantitySold.toString().padEnd(cols[5])} ${detailDescription.padEnd(cols[6])}"
    }

    open fun toTab(): String {
        return "$partCategory\t$name\t$retailPrice\t$cost\t$quantityStock\t$quantitySold\t$detailDescription\t"
    }

    open fun showDetails(): String {
        return "-".repeat(50) + "\n" + """
            Part's Category:         $partCategory
            Part Name:               $name
            Retail Price:            ${retailPrice.toDollar()}
            Cost:                    ${cost.toDollar()}
            QTY In Stock:            $quantityStock
            QTY Sold:                $quantitySold
            Detailed Description:    $detailDescription
        """.trimIndent() + "\n"
    }

    open fun showGrandTotal(cols: Array<Int>): String {
        return "${partCategory.padEnd(cols[0])} ${name.padEnd(cols[1])} ${
            retailPrice.toDollar().padEnd(cols[2])
        } ${cost.toDollar().padEnd(cols[3])} ${
            quantityStock.toString().padEnd(cols[4])
        } ${
            quantitySold.toString().padEnd(cols[5])
        } ${detailDescription.padEnd(cols[6])} ${
            (retailPrice - cost).toDollar().padStart(cols[2])
        } ${((retailPrice - cost) * quantitySold).toDollar().padStart(cols[2])}"
    }
}