package com.company.xchange.screen.key

import com.company.xchange.entity.Blockchain
import com.company.xchange.entity.Key
import com.company.xchange.service.EthereumService
import com.company.xchange.service.WavesService
import com.wavesplatform.transactions.account.PrivateKey
import io.jmix.ui.Dialogs
import io.jmix.ui.action.Action
import io.jmix.ui.app.inputdialog.DialogActions
import io.jmix.ui.app.inputdialog.DialogOutcome
import io.jmix.ui.app.inputdialog.InputParameter
import io.jmix.ui.model.CollectionContainer
import io.jmix.ui.screen.*
import org.springframework.beans.factory.annotation.Autowired

@UiController("xc_Key.browse")
@UiDescriptor("key-browse.xml")
@LookupComponent("keysTable")
class KeyBrowse : StandardLookup<Key>() {
    @Autowired
    private lateinit var dialogs: Dialogs

    @Autowired
    private lateinit var wavesService: WavesService

    @Autowired
    private lateinit var keysDc: CollectionContainer<Key>

    @Autowired
    private lateinit var ethereumService: EthereumService

    @Install(to = "keysTable.address", subject = "valueProvider")
    private fun keysTableAddressValueProvider(key: Key): String? {
        return when(key.getBlockchain()) {
            Blockchain.WAVES -> {
                val privateKeyStr = key.privateKey
                PrivateKey.`as`(privateKeyStr).address(wavesService.getChainId()).toString()
            }
            else -> null
        }
    }

    @Subscribe("keysTable.generateKey")
    private fun onKeysTableGenerateKey(event: Action.ActionPerformedEvent) {
        dialogs
            .createInputDialog(this)
            .withParameters(
                InputParameter.enumParameter("blockchainParam", Blockchain::class.java)
                    .withRequired(true)
            ).withActions(DialogActions.OK_CANCEL)
            .withCloseListener {
                if (it.closedWith(DialogOutcome.OK)) {
                    val blockchain = it.getValue<Blockchain>("blockchainParam") ?: return@withCloseListener
                    val key = when (blockchain) {
                        Blockchain.WAVES -> wavesService.createKeyIfNotExists()
//                        Blockchain.ETHEREUM -> ethereumService.createKeyIfNotExists()
                        else -> null
                    }
                    key?.let { keysDc.replaceItem(key) }
                }
            }
            .show()
    }
}