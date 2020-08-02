package com.alestrio.bitcoinchart.gui.launcher

import com.alestrio.bitcoinchart.utils.LanguageManager
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

private val i18n = LanguageManager()
class LauncherView : View(i18n.getMessage("launcher_title")) {
    private val controller : LauncherController by inject()
    private val addressInput = SimpleStringProperty()

    override val root = vbox {
        label(i18n.getMessage("welcome"))
        form {
            fieldset {
                field(i18n.getMessage("base_address")) {
                    textfield(addressInput)
                }
                //Validation button
                button(i18n.getMessage("validate")){
                    action {
                        controller.executeQuery(addressInput.get())
                    }
                }
            }
        }
    }
}

