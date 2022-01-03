package com.company.xchange.service

import com.company.xchange.entity.Key
import org.web3j.protocol.core.methods.response.EthBlockNumber
import org.web3j.protocol.core.methods.response.Web3ClientVersion
import org.web3j.protocol.core.methods.response.admin.AdminNodeInfo
import java.math.BigDecimal
import java.math.BigInteger

interface EthereumService: BlockchainService {

    companion object {
        const val NAME = "xc_EthereumService"
    }

    fun getBalance(): BigInteger?
    fun sendCoins(publicKey: String, value: BigDecimal)
    fun getBlockNumber(): EthBlockNumber?
}