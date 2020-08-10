/*Test file*/
package com.alestrio.bitcoinchart.blockchainapi

suspend fun main(args: Array<String>) {
    val api = BlockchainInfo()
    print(api.getResponseAsChart("1G47mSr3oANXMafVrR8UC4pzV7FEAzo3r9", 3))

}