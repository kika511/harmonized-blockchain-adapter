package com.company.xchange.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(BlockchainConf::class)
open class XcConf {
    @Autowired
    lateinit var blockchainConf: BlockchainConf
}