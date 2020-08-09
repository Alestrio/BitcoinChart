/*View class of the first window of the program intended to get user's directives such as address to probe, recurrences...*/
package com.alestrio.bitcoinchart.gui.launcher

import com.alestrio.bitcoinchart.utils.LanguageManager
import javafx.beans.property.SimpleStringProperty
import tornadofx.*


class LauncherView(private val i18n: LanguageManager = LanguageManager()) : View(i18n.getMessage("launcher_title")) {
    // Controller and field variables
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
                        fun sanityCheck() : Boolean{
                            /*Function checking if user input is everything but null and ready to be sent to the controller*/
                            return when{
                                addressInput.get() == null -> false
                                else -> true
                            }
                        }
                        if(sanityCheck()) controller.executeQuery(addressInput.get())
                    }
                }
            }
        }
    }

}

