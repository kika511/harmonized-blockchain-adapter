package com.company.xchange.entity

import io.jmix.core.entity.annotation.JmixGeneratedValue
import io.jmix.core.entity.annotation.SystemLevel
import io.jmix.core.metamodel.annotation.DependsOnProperties
import io.jmix.core.metamodel.annotation.InstanceName
import io.jmix.core.metamodel.annotation.JmixEntity
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@JmixEntity
@Table(name = "XC_KEY", indexes = [
    Index(name = "IDX_XC_KEY_ON_USER_AND_BLOCKCHAIN", columnList = "USER_ID, BLOCKCHAIN", unique = true)
])
@Entity(name = "xc_Key")
open class Key {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    var id: UUID? = null

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    var user: User? = null

    @NotNull
    @Column(name = "BLOCKCHAIN", nullable = false)
    private var blockchain: String? = null

    @NotNull
    @SystemLevel
    @Column(name = "PRIVATE_KEY", nullable = false)
    var privateKey: String? = null

    @NotNull
    @Column(name = "PUBLIC_KEY", nullable = false)
    var publicKey: String? = null

    fun getBlockchain(): Blockchain? {
        return blockchain?.let { Blockchain.fromId(it) }
    }

    fun setBlockchain(blockchain: Blockchain) {
        this.blockchain = blockchain.id
    }

    @InstanceName
    @DependsOnProperties("user", "blockchain")
    fun getInstanceName(): String = "${user ?: ""} ${blockchain ?: ""}".trim()
}