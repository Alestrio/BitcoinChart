/*Base class for blockchain api services*/
package com.alestrio.bitcoinchart.blockchainapi

import io.ktor.client.HttpClient

abstract class ApiProvider(protected var serviceAddress: String) {
    protected val client = HttpClient()

    protected abstract fun generateFirstRequest(address:String)
    protected abstract fun generateChildRequests(addresses:ArrayList<String>)
    protected abstract fun parseResponse(response: String, recurrences: Int)
    protected abstract suspend fun sendRequest(request: String) : String
    abstract suspend fun getResponseAsChart(address: String, recurrences : Int)
    abstract fun checkConnection()

}