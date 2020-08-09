/*Class creating the request to send to the remote service to create the chart*/
package com.alestrio.bitcoinchart.charting

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class ChartCreator(var chart: Chart) {

    /*Method to build the quickchart.io request*/
    private fun generateRequestBody(): String{
        var body = "{ \n"
        body += "\"width\":" + chart.width + ",\n"
        body += "\"height\":" + chart.height + ",\n"
        body += "\"format\":\"png\", \n"
        body += "\"graph\": \""
        body += this.generateDigraph()
        body += "\n}"

        return body
    }

    /*Method to build the digraph*/
    private fun generateDigraph(): String{
        var digraph = "digraph {"
        chart.orphanNodes.forEach{
            digraph += it.iterate().dropLast(1)
        }
        return "$digraph}\""
    }

    /*Sending request and saving response as a PNG*/
    suspend fun createChart(){
        val client = HttpClient()
        val response =  client.post<ByteArray>("https://quickchart.io/graphviz") {
            header("Content-Type", "application/json")
            body = generateRequestBody()
        }
        this.saveChart(response)
    }

    /*Saving response as a png*/
    private fun saveChart(image: ByteArray){
        val imageFile = File("chart.png")
        if (!imageFile.exists()) imageFile.createNewFile()
        val stream = imageFile.outputStream()
        try {
            stream.write(image)
            stream.close()
        }catch(e:FileNotFoundException){
            print("File not found")
        }
        catch (e:IOException){
            print("Output error")
        }

    }
}