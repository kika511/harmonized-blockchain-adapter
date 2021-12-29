package com.company.xchange.service.impl

import com.company.xchange.entity.Blockchain
import com.company.xchange.entity.Key
import com.company.xchange.entity.User
import com.company.xchange.service.WavesService
import com.wavesplatform.crypto.Crypto
import com.wavesplatform.transactions.TransferTransaction
import com.wavesplatform.transactions.account.Address
import com.wavesplatform.transactions.account.PrivateKey
import com.wavesplatform.transactions.account.PublicKey
import com.wavesplatform.transactions.common.Amount
import com.wavesplatform.transactions.common.AssetId
import com.wavesplatform.transactions.common.ChainId
import com.wavesplatform.wavesj.Block
import com.wavesplatform.wavesj.BlockHeaders
import com.wavesplatform.wavesj.Node
import com.wavesplatform.wavesj.Profile
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service(WavesService.NAME)
class WavesServiceBean(
    override val blockchain: Blockchain = Blockchain.WAVES
) : WavesService, BaseBlockchainService() {

    private lateinit var node: Node

    @PostConstruct
    fun postConstruct() {
        node = Node(getProfile())
        logger.info("Connected to Waves client version: " + node.version)
    }

    override fun getBalance(): Long {
        val privateKeyStr = currentUserKeyOrThrowException().privateKey
        val privateKey = PrivateKey.`as`(privateKeyStr)
        val address = privateKey?.address(getChainId())
        return node.getBalance(address)
    }

    override fun getBlockchainNode(): Node {
        return node
    }

    override fun getLastBlock(): Block {
        return node.lastBlock
    }

    override fun getLastBlockHeaders(): BlockHeaders {
        return node.lastBlockHeaders
    }

    // value 1_00_000_000 = to 1 WAVE coin
    override fun sendCoin(publicKey: String, value: Long) {
        val recipient = Address(getChainId(), PublicKey(publicKey))
        val privateKeyStr = currentUserKeyOrThrowException().privateKey

        val transaction = node.broadcast(
            TransferTransaction.builder(
                recipient,
                Amount.of(value, AssetId.WAVES)
            ).getSignedWith(PrivateKey.`as`(privateKeyStr))
        )
        logger.info("WAVES Transaction to $publicKey complete, transaction Id: "
                + transaction.id())
    }

    override fun createKeyIfNotExists(): Key {
        val user = currentAuthentication.user as User
        val key = user.getKey(Blockchain.WAVES)

        return if (key == null) {
            val seed = Crypto.getRandomSeedPhrase()
            val privateKey = PrivateKey.fromSeed(seed)
            val publicKey = PublicKey.from(privateKey)
            val newKey = dataManager.create(Key::class.java)
            newKey.user = user
            newKey.setBlockchain(Blockchain.WAVES)
            newKey.privateKey = privateKey.encoded()
            newKey.publicKey  = publicKey.encoded()

            dataManager.save(newKey)
        } else key
    }

    fun getProfile(): Profile {
        return when (blockchainConf.net) {
            "test" -> Profile.TESTNET
            "main" -> Profile.MAINNET
            else -> throw Exception("No such blockchain net: " + blockchainConf.net)
        }
    }

    override fun getChainId(): Byte {
        return when (blockchainConf.net) {
            "test" -> ChainId.TESTNET
            "main" -> ChainId.MAINNET
            else -> throw Exception("No such blockchain net: " + blockchainConf.net)
        }
    }
}