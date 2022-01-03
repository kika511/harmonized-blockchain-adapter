package com.company.xchange.service.impl

import com.company.xchange.entity.Blockchain
import com.company.xchange.service.BitcoinService
import org.bitcoinj.core.Address
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.kits.WalletAppKit
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.wallet.KeyChainGroup
import org.bitcoinj.wallet.Wallet
import org.springframework.stereotype.Component
import java.io.File
import java.math.BigInteger
import javax.annotation.PostConstruct

@Component(BitcoinService.NAME)
class BitcoinServiceBean(
    override val blockchain: Blockchain = Blockchain.BITCOIN
) : BitcoinService, BaseBlockchainService() {

    private lateinit var params: NetworkParameters
    private lateinit var filePrefix: String

    @PostConstruct
    fun postConstruct() {
        params = getNetworkParameters()
        filePrefix = getFilePrefix()
    }

//    fun getBalance(): Long {
//        val key = currentUserKeyOrThrowException()
//        val address = Address.fromString(params, key.publicKey)
//        val fromPrivate = ECKey.fromPrivate(BigInteger(key.privateKey!!))
//        KeyChainGroup.builder(params).fromKey()
//        val wallet = Wallet(params)
//        wallet.importKey(key)
//    }

    fun getNetworkParameters(): NetworkParameters {
        return when (blockchainConf.net) {
            "test" -> TestNet3Params.get()
            "main" -> MainNetParams.get()
            else -> throw Exception("No such blockchain net: " + blockchainConf.net)
        }
    }

    private fun getFilePrefix(): String {
        return when (blockchainConf.net) {
            "test" -> "forwarding-service-testnet"
            "main" -> "forwarding-service"
            else -> throw Exception("No such blockchain net: " + blockchainConf.net)
        }
    }
}