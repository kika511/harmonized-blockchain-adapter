package com.company.xchange.screen.blockchain.waves

import com.company.xchange.service.WavesService
import io.jmix.ui.Notifications
import io.jmix.ui.component.Button
import io.jmix.ui.component.TextField
import io.jmix.ui.screen.*
import org.springframework.beans.factory.annotation.Autowired

@UiController("WavesScreen")
@UiDescriptor("waves-screen.xml")
class WavesScreen : Screen() {
    @Autowired
    private lateinit var wavesService: WavesService

    @Autowired
    private lateinit var notifications: Notifications

    @Autowired
    private lateinit var valueFiled: TextField<Long>

    @Autowired
    private lateinit var addressField: TextField<String>

    @Autowired
    private lateinit var screenValidation: ScreenValidation

    @Subscribe("testNodeBtn")
    private fun onTestNodeBtnClick(event: Button.ClickEvent) {
        wavesService.getBlockchainNode()
    }

    @Subscribe("getBalanceBtn")
    private fun onGetBalanceBtnClick(event: Button.ClickEvent) {
        val balance = wavesService.getBalance()
        notifications.create()
            .withCaption(balance.toString())
            .show()
    }

    @Subscribe("sendCoinsBtn")
    private fun onSendCoinsBtnClick(event: Button.ClickEvent) {
        val errors = screenValidation.validateUiComponents(listOf(addressField, valueFiled))
        if (errors.isEmpty) {
            val address = addressField.value ?: return
            val value = valueFiled.value ?: return
            wavesService.sendCoin(address, value)
        } else {
            screenValidation.showValidationErrors(this, errors);
        }
    }
}