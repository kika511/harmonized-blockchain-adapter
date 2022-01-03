package com.company.xchange.service

import com.company.xchange.entity.Key
import com.wavesplatform.wavesj.Block
import com.wavesplatform.wavesj.BlockHeaders
import com.wavesplatform.wavesj.Node

interface WavesService {

    companion object {
        const val NAME = "xc_WavesService"
    }

    fun getBalance(): Long
    fun sendCoin(publicKey: String, value: Long)
    fun createKeyIfNotExists(): Key
    fun getChainId(): Byte
    fun getLastBlock(): Block
    fun getLastBlockHeaders(): BlockHeaders
    fun getBlockchainNode(): Node
}