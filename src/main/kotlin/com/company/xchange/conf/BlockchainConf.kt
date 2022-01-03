package com.company.xchange.conf

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "xc.blockchain")
data class BlockchainConf(
    val net: String,
    val ethereumUrl: String
)