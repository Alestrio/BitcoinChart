package com.alestrio.bitcoinchart.charting

suspend fun main(args: Array<String>){
    val chartContent = ArrayList<TreeNode>()
    val branch = TreeBranch(ChartItem("1", 120.0, "1", true), true)
    branch.addChild(ChartItem("2", 400.0, "2", false))
    branch.addChild(ChartItem("3", 800.0, "3", false))
    val branch1 = TreeBranch(ChartItem("4", 700.0, "4", false), false)
    branch1.addChild(ChartItem("5", 666.0, "5", false))
    branch1.addChild(ChartItem("6", 69.0, "6", false))
    branch1.addChild(ChartItem("7", 42.0, "7", false))
    branch.addChild(branch1)
    chartContent.add(branch)
    var myChart = Chart("Test", chartContent, 1600, 1600)
    val creator = ChartCreator(myChart)
    creator.createChart()
}