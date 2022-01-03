package com.company.xchange.service.impl

import com.company.xchange.conf.BlockchainConf
import com.company.xchange.entity.Blockchain
import com.company.xchange.entity.Key
import com.company.xchange.entity.User
import com.wavesplatform.transactions.account.PrivateKey
import io.jmix.core.DataManager
import io.jmix.core.security.CurrentAuthentication
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
abstract class BaseBlockchainService {

    @Autowired
    protected lateinit var currentAuthentication: CurrentAuthentication

    @Autowired
    protected lateinit var blockchainConf: BlockchainConf

    @Autowired
    protected lateinit var dataManager: DataManager

    protected abstract val blockchain: Blockchain

    protected fun currentUserKeyOrThrowException() =
        getKey() ?: throw Exception("CurrentUser do not have address for $blockchain")

    private fun getKey(): Key? {
        val user = currentAuthentication.user as User
        return user.getKey(blockchain)
    }

    companion object : KLogging()
}