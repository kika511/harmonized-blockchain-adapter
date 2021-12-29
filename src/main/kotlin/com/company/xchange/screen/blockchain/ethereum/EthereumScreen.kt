package com.company.xchange.screen.blockchain.ethereum

import com.company.xchange.service.EthereumService
import io.jmix.ui.Notifications
import io.jmix.ui.component.Button
import io.jmix.ui.component.TextField
import io.jmix.ui.screen.*
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal

@UiController("EthereumScreen")
@UiDescriptor("ethereum-screen.xml")
class EthereumScreen : Screen() {
    @Autowired
    private lateinit var ethereumService: EthereumService

    @Autowired
    private lateinit var notifications: Notifications

    @Autowired
    private lateinit var screenValidation: ScreenValidation

    @Autowired
    private lateinit var addressField: TextField<String>

    @Autowired
    private lateinit var valueFiled: TextField<BigDecimal>

    @Subscribe("getBalance")
    private fun onGetBalanceClick(event: Button.ClickEvent) {
        val balance = ethereumService.getBalance()
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
            ethereumService.sendCoins(address, value)
        } else {
            screenValidation.showValidationErrors(this, errors);
        }
    }

}