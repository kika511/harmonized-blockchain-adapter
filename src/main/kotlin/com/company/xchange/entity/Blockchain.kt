package com.company.xchange.entity

import io.jmix.core.metamodel.datatype.impl.EnumClass

enum class Blockchain(private val id: String) : EnumClass<String> {

    WAVES("WAVES"),
    ETHEREUM("ETHEREUM"),
    BITCOIN("BITCOIN");

    override fun getId() = id

    companion object {

        @JvmStatic
        fun fromId(id: String): Blockchain? = values().find { it.id == id }
    }
}