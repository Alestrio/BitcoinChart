package com.alestrio.bitcoinchart.gui.result

import com.alestrio.bitcoinchart.gui.launcher.LauncherView
import com.alestrio.bitcoinchart.utils.LanguageManager
import javafx.scene.Parent
import tornadofx.*

private val i18n = LanguageManager()
class ResultView(val width : Double, val height : Double) : View(i18n.getMessage("results_title")) {
    val controller : ResultController by inject()
    override val root = vbox{
        imageview("chart.png"){
            setPrefSize(width, height)
        }
        buttonbar{
            button(i18n.getMessage("quit")){
                action {
                    controller.quitApp()
                }
            }
            button(i18n.getMessage("return_to_launcher")){
                action {
                    replaceWith<LauncherView>()
                }
            }
        }
    }
}