package com.alestrio.bitcoinchart.gui

import com.alestrio.bitcoinchart.utils.LanguageManager
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Launcher : View("BitcoinChart - Config") {
    private val i18n = LanguageManager()
    private val addressInput = SimpleStringProperty()
    override val root = vbox {
        label(i18n.getMessage("welcome"))
        form {
            fieldset {
                field(i18n.getMessage("base_address")) {
                    textfield(addressInput)
                }
            }
        }
    }
}
