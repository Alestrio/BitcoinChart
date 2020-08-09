/*Implementation of ApiProvider for Blockchain.info api provider*/
package com.alestrio.bitcoinchart.blockchainapi

import com.alestrio.bitcoinchart.charting.ChartItem
import com.alestrio.bitcoinchart.charting.TreeBranch
import com.beust.klaxon.Klaxon
import io.ktor.client.request.header
import io.ktor.client.request.post

class BlockchainInfo : ApiProvider("https://blockchain.info/") {
    /*Suffixes needed to generate request*/
    private val singleAddressSuffix = "rawaddr/"
    private val singleTxSuffix = "rawtx/"

    override fun generateFirstRequest(address: String) {

    }

    override fun generateChildRequests(addresses: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun parseResponse(response: String, recurrences: Int) {
        val parsedResponse = Klaxon().parse<BlockchainInfoItem>(response)
        val baseBranch = TreeBranch(ChartItem(parsedResponse!!.address,
                                              parsedResponse.final_balance,
                                              isOrphan = true), true)


    }

    override suspend fun sendRequest(request:String): String {
        val requestAddress = this.serviceAddress + singleAddressSuffix
        return client.post(requestAddress) {
            header("Content-Type", "application/json")
            body = request
        }
    }

    override suspend fun getResponseAsChart(address: String, recurrences : Int) {
        val response = this.sendRequest(address)
        return parseResponse(response, recurrences)
    }

    override fun checkConnection() {
        TODO("Not yet implemented")
    }
}

/* Data classes for JSON serializer */
/* ------------ BEGIN ------------*/

data class BlockchainInfoItem(val hash160:String,
                              val address:String,
                              val n_tx:Int,
                              val n_unredeemed:Int,
                              val total_received:Double,
                              val total_sent:Double,
                              val final_balance:Double, val txs:ArrayList<BlockchainInfoTransactions>){

    data class BlockchainInfoTransactions(val hash:String,
                                          val ver:Int,
                                          val vin_sz:Int,
                                          val vout_sz:Int,
                                          val lock_time:String,
                                          val size:Int,
                                          val relayed_by:String,
                                          val block_height:Int,
                                          val tx_index:Int,
                                          val inputs:ArrayList<BlockchainInfoInput>,
                                          val out:ArrayList<BlockchainInfoOut>){

        data class BlockchainInfoInput(val pre_out:ArrayList<BlockchainInfoPrevout>,
                                       val script:String) {
            data class BlockchainInfoPrevout(val hash:String,
                                             val value:String,
                                             val tx_index:String,
                                             val n:String)
        }

        data class BlockchainInfoOut(val value:String,
                                     val hash:String,
                                     val script:String)
    }
}



/*------------ END ------------*/
