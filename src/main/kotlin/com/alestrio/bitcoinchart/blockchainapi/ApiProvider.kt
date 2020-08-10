/*Base class for blockchain api services*/
package com.alestrio.bitcoinchart.blockchainapi

import io.ktor.client.HttpClient

abstract class ApiProvider(protected var serviceAddress: String) {
    protected val client = HttpClient()

    protected abstract suspend fun generateFirstRequest(address:String): String
    protected abstract fun generateChildRequests(addresses: Set<String>): String
    protected abstract suspend fun parseResponse(response: String, recurrences: Int)
    protected abstract suspend fun sendRequest(request: String) : String
    abstract suspend fun getResponseAsChart(address: String, recurrences : Int): String
    abstract fun checkConnection()

}