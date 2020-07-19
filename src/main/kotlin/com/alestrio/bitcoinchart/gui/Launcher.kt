package com.alestrio.bitcoinchart.gui

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Launcher : View("BitcoinChart - Config") {
    private val addressInput = SimpleStringProperty()
    override val root = vbox {
        label("Bienvenue dans l'interface de configuration de BitcoinChart (BTCC) !")
        form {
            fieldset {
                field("Addresse à sonder :") {
                    textfield(addressInput)
                }
            }
        }
    }
}
