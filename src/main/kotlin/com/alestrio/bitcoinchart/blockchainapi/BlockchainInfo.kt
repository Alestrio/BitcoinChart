/*Implementation of ApiProvider for Blockchain.info api provider*/
package com.alestrio.bitcoinchart.blockchainapi

import com.alestrio.bitcoinchart.charting.ChartItem
import com.alestrio.bitcoinchart.charting.TreeBranch
import com.beust.klaxon.Klaxon
import io.ktor.client.request.get
import sun.reflect.generics.tree.Tree

class BlockchainInfo : ApiProvider("https://blockchain.info/") {
    /*Suffixes needed to generate request*/
    private val singleAddressSuffix = "rawaddr/"
    private val multipleAddressesSuffix = "multiaddr?active="
    private val singleTxSuffix = "rawtx/"

    override suspend fun generateFirstRequest(address: String): String {
        return this.serviceAddress + singleAddressSuffix + address
    }

    override fun generateChildRequests(addresses: Set<String>): String {
        TODO("Not yet implemented")
    }

    override suspend fun parseResponse(response: String, recurrences: Int) {
        val parsedResponse = Klaxon().parse<BlockchainInfoItem>(response)
        val baseBranch = TreeBranch(ChartItem(parsedResponse!!.address,
                parsedResponse.final_balance,
                isOrphan = true), true)
        //Checks if the address is one of the input of the transaction
        fun checkIfInput(address:String, tx:BlockchainInfoItem.BlockchainInfoTransactions): Boolean{
            var result = false
            tx.inputs.forEach {
                if (it.addr == address) result = true
            }
            return result
        }
        //Checks if the address is one of the outputs of the transaction
        fun checkIfOutput(address: String, tx:BlockchainInfoItem.BlockchainInfoTransactions): Boolean{
            var result = false
            tx.out.forEach {
                if (it.addr == address) result = true
            }
            return result
        }
        //Parses the transaction to a TreeBranch and adding it to the baseBranch
        fun parseTransactionsAndAddToBaseBranch(query:String, item:ChartItem){
            // Requesting Transactions of the specified wallet
            val txs = Klaxon().parse<BlockchainInfoItem>(query)!!.txs
            val subBranch = TreeBranch(item, false)
            txs.forEach {
                //Checking if the address is present in outputs or inputs
                when{
                    //If the item is one of the outputs of the transaction
                    !checkIfInput(item.address, it) and checkIfOutput(item.address, it) -> {

                    }
                }
                val baseNode = Klaxon().parse<BlockchainInfoItem>("lowLevelChildren")
                subBranch.addChild(TreeBranch(ChartItem(baseNode!!.address, baseNode.final_balance)))
                baseBranch.addChild(subBranch)
            }
        }

        for(i in 0..recurrences){
            val lowLevelChildren = baseBranch.getlowLevelChartItems()
            when (lowLevelChildren) {
                is ChartItem -> {
                    val transactionsQuery = this.sendRequest(this.generateChildRequests(setOf<String>(lowLevelChildren.address)))
                    parseTransactionsAndAddToBaseBranch(transactionsQuery, lowLevelChildren)
                }
                is ArrayList<*> -> {
                    (lowLevelChildren as ArrayList<ChartItem>).forEach {
                        val transactionsQuery = this.sendRequest(this.generateChildRequests(setOf<String>(it.address)))
                        parseTransactionsAndAddToBaseBranch(transactionsQuery, it)
                    }
                }
                else -> throw Exception("Parsing error")
            }
        }

    }

    override suspend fun sendRequest(requestAddress:String): String {
        return client.get<String>(requestAddress)
    }

    override suspend fun getResponseAsChart(address: String, recurrences : Int): String {
        val response = this.sendRequest(this.generateFirstRequest(address))
        //return parseResponse(response, recurrences)
        return response
    }

    override fun checkConnection() {
        TODO("Not yet implemented")
    }
}

/* Data classes for JSON serializer */
/* ref in https://www.blockchain.com/fr/api/blockchain_api */
/* ------------ BEGIN ------------*/

data class BlockchainInfoItem(val hash160:String,
                              val address:String,
                              val n_tx:Int,
                              val n_unredeemed:Int,
                              val total_received:Double,
                              val total_sent:Double,
                              val final_balance:Double,
                              val txs:ArrayList<BlockchainInfoTransactions>){

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
                                       val script:String,
                                       val addr:String) {
            data class BlockchainInfoPrevout(val hash:String,
                                             val value:String,
                                             val tx_index:String,
                                             val n:String)
        }

        data class BlockchainInfoOut(val value:String,
                                     val hash:String,
                                     val script:String,
                                     val addr:String)
    }
}



/*------------ END ------------*/
