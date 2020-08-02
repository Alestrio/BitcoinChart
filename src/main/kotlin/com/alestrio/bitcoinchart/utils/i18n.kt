package com.alestrio.bitcoinchart.utils

import com.sun.org.apache.xerces.internal.parsers.DOMParser
import java.io.File
import java.io.FileInputStream
import java.lang.NullPointerException
import java.util.*

class LanguageManager(var filePrimitivePath: String = "l10n/strings", var fileExtension: String = "properties") {
    var selectedLanguage = "en"
    var languages = setOf("en", "fr")


    fun setLanguage(lg:String){
       if(lg in languages){
           this.selectedLanguage = lg
       }
        else{
           throw Exception("This language is not supported (yet)")
       }
    }

    fun getMessage(key:String):String{
        val props = this.loadProperties()
        val message:String? = props.getProperty(key)
        return message!!
    }


    private fun loadProperties():Properties{
        val props = Properties()
        try {
            props.load(FileInputStream(this.filePrimitivePath + "_" + this.selectedLanguage + "." + this.fileExtension))
        } catch (e:NullPointerException){
            println("File not found")
            println(this.filePrimitivePath + "_" + this.selectedLanguage + "." + this.fileExtension)
        }
        return props
    }
}
