package com.alestrio.bitcoinchart.charting

import java.lang.Exception

class ChartItem(var address: String, var balance: Double, var name: String, isOrphan: Boolean) : TreeNode(isOrphan) {

    /* Returns that item as a digraph-typed String*/
    override fun iterate(): String {
        return "" + this.address + ""
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