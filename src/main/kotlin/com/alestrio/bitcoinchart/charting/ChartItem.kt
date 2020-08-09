/*Implementation of TreeNode intended to store a single wallet (identified by it's address)
without outgoing transactions (or limited by recurrences number)*/
package com.alestrio.bitcoinchart.charting

import java.lang.Exception

class ChartItem(private var address: String, private var balance: Double, var name: String = "Unknown", isOrphan: Boolean) : TreeNode(isOrphan) {

    /* Returns that item as a digraph-typed String*/
    override fun iterate(): String {
        return "" + this.address + ""
    }

    override fun getlowLevelChartItems(): Any {
        return this
    }

    init{
        this.isValid()
    }

    /*Method to see whether the Item is valid or not*/
    private fun isValid(){
        var isValid = true
        when{
            this.address == "" -> isValid = false
            this.balance == 0.0 -> isValid = false
        }
        if (!isValid) throw Exception("ChartItem is not valid ! Try checking the provided parameters !")
    }

    fun depth(){
        TODO("Depth")
    }

}