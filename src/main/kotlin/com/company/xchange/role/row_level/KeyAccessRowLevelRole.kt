package com.company.xchange.role.row_level

import com.company.xchange.entity.Key
import io.jmix.security.role.annotation.RowLevelRole
import io.jmix.security.role.annotation.JpqlRowLevelPolicy


@RowLevelRole(
    name = "Key Access Row Level Role",
    code = "key-row-level-access",
    description = "Users with this role can see only their own keys")
interface KeyAccessRowLevelRole {
    @JpqlRowLevelPolicy(
        entityClass = Key::class,
        where = "{E}.user.username = :current_user_username"
    )
    fun key()
}