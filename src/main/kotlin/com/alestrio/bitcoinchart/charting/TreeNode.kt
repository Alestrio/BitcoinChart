/*Class used to define tree nodes to build the chart*/
package com.alestrio.bitcoinchart.charting

abstract class TreeNode (var isOrphan: Boolean){
    abstract fun iterate(): String
}