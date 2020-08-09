/*Implementation of TreeNode intended to store wallets with outgoing transactions*/
package com.alestrio.bitcoinchart.charting

class TreeBranch (var startNode: ChartItem, isOrphan: Boolean) : TreeNode(isOrphan) {
    var children: ArrayList<TreeNode> = ArrayList()

    /*Method to add a child to the branch*/
    fun addChild(childNode: TreeNode){
        children.add(childNode)
    }

    /*Method to compute the size of the branch*/
    private fun size(){

    }

    /*Iterate through the tree and returns that content as a digraph-typed String*/
    override fun iterate(): String {
        var digraphStructure = ""
        children.forEach { child ->
            digraphStructure += startNode.iterate() + "->"
            digraphStructure += child.iterate() + ";"
        }
        return digraphStructure
    }

    override fun getlowLevelChartItems(): Any {
        var deeperLevel = ArrayList<ChartItem>()
        children.forEach {val temp = it.getlowLevelChartItems()
            if (temp is ChartItem) deeperLevel.add(temp)
            else if (temp is ArrayList<*>) deeperLevel.addAll(temp as Collection<ChartItem>)
            else throw Exception("Type Error")
            }
        return if (deeperLevel.size == 1) deeperLevel[0] else deeperLevel
    }

}