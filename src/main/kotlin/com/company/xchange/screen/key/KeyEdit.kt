package com.company.xchange.screen.key

import com.company.xchange.entity.Blockchain
import com.company.xchange.entity.Key
import com.company.xchange.service.WavesService
import com.wavesplatform.transactions.account.PrivateKey
import io.jmix.ui.component.TextField
import io.jmix.ui.screen.*
import org.springframework.beans.factory.annotation.Autowired

@UiController("xc_Key.edit")
@UiDescriptor("key-edit.xml")
@EditedEntityContainer("keyDc")
class KeyEdit : StandardEditor<Key>() {
    @Autowired
    private lateinit var address: TextField<*>

    @Autowired
    private lateinit var wavesService: WavesService

    @Subscribe
    private fun onBeforeShow(event: BeforeShowEvent) {
        address.value = when(editedEntity.getBlockchain()) {
            Blockchain.WAVES -> PrivateKey.`as`(editedEntity.privateKey)?.address(wavesService.getChainId()).toString()
            else -> null
        }
    }

}