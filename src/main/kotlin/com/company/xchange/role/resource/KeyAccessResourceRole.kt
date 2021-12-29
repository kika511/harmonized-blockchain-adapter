package com.company.xchange.role.resource

import com.company.xchange.entity.Key
import io.jmix.security.role.annotation.ResourceRole
import io.jmix.security.model.EntityPolicyAction
import io.jmix.security.role.annotation.EntityPolicy
import io.jmix.securityui.role.annotation.MenuPolicy
import io.jmix.securityui.role.annotation.ScreenPolicy


@ResourceRole(
    name = "Key Access Resource Role",
    code = "key-resource-access",
    description = "Gives access to keys entity and keys screen")
interface KeyAccessResourceRole {

    @EntityPolicy(
        entityClass = Key::class,
        actions = [EntityPolicyAction.READ, EntityPolicyAction.CREATE]
    )
    @ScreenPolicy(
        screenIds = ["xc_Key.browse"]
    )
    @MenuPolicy(
        menuIds = ["xc_Key.browse"]
    )
    fun key()
}