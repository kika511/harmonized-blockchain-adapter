package com.company.xchange.service.impl

import com.company.xchange.entity.Blockchain
import com.company.xchange.service.EthereumService
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthBlockNumber
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Service(EthereumService.NAME)
class EthereumServiceBean(
    override val blockchain: Blockchain = Blockchain.ETHEREUM
) : EthereumService, BaseBlockchainService() {

    private lateinit var web3: Web3j

    @PostConstruct
    fun postConstruct() {
        web3 = Web3j.build(HttpService(blockchainConf.ethereumUrl))
        logger.info("Connected to Ethereum client version: " + web3.web3ClientVersion().send().web3ClientVersion)
    }

    override fun getBlockNumber(): EthBlockNumber? {
        return web3.ethBlockNumber().send()
    }

    // 1 ETH = 1_000_000_000_000_000_000
    override fun getBalance(): BigInteger? {
        val credentials = currentUserCredentials()
        return web3.ethGetBalance(credentials.address, DefaultBlockParameterName.LATEST).send().balance
    }

    // value 1 = to 1 ETHER coin
    override fun sendCoins(publicKey: String, value: BigDecimal) {
        val transferReceipt = Transfer.sendFunds(
            web3, currentUserCredentials(),
            publicKey, // you can put any address here
            value, Convert.Unit.ETHER
        ).sendAsync().get()
        logger.info("ETHER Transaction to $publicKey complete, transactionHash: "
                + transferReceipt.transactionHash)
    }

//    override fun createKeyIfNotExists(): Key {
//        val user = currentAuthentication.user as User
//        val key = user.getKey(Blockchain.ETHEREUM)
//
//        return if (key == null) {
//            val createEcKeyPair = Keys.createEcKeyPair()
//            val walletFile = Wallet.createLight(password, createEcKeyPair)
//            val newKey = dataManager.create(Key::class.java)
//            newKey.user = user
//            newKey.setBlockchain(Blockchain.WAVES)
//            newKey.privateKey = createEcKeyPair.privateKey.toString()
//            newKey.publicKey  = createEcKeyPair.publicKey.toString()
//
//            dataManager.save(newKey)
//        } else key
//    }

    private fun currentUserCredentials(): Credentials {
        val key = currentUserKeyOrThrowException()
        return Credentials.create(key.privateKey)
    }

    @PreDestroy
    fun preDestroy() {
        web3.shutdown()
    }

}