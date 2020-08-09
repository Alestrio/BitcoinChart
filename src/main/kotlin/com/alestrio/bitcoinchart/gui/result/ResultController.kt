/*Controller class of the result window of the program intended to show the result as a chart*/
package com.alestrio.bitcoinchart.gui.result

import tornadofx.*

class ResultController : Controller() {
    fun quitApp(){
        this.app.stop()
    }
}