package com.company.xchange.route


//@Component
//class MyRoute: RouteBuilder() {
//
//    override fun configure() {
//        from("timer:base?period=60000")
//            .routeId("binance ETH/USDT")
//            .to("xchange:binance?service=marketdata&method=ticker&currencyPair=ETH/USDT")
//            .log("\${body}")
//
//        from("timer:base?period=60000")
//            .routeId("binance BTC/USDT")
//            .to("xchange:binance?service=marketdata&method=ticker&currencyPair=BTC/USDT")
//            .log("\${body}")
//
//        from("timer:base?period=60000")
//            .routeId("binance WAVES/USDT")
//            .to("xchange:binance?service=marketdata&method=ticker&currencyPair=WAVES/USDT")
//            .log("\${body}")
//
//
////        val simpleRegistry = SimpleRegistry()
////        simpleRegistry["apiKey"] = mapOf(Pair(String::class.java, apiKey))
////        simpleRegistry["secretKey"] = mapOf(Pair(String::class.java, secretKey))
//
//
//        from("timer:base?period=60000")
//            .routeId("binance wallet")
//            .process {
//                it.setProperty("apiKey", apiKey)
//                it.setProperty("secretKey", secretKey)
//            }.to("xchange:binance?service=account&method=balances")
//            .log("\${body}")
//    }
//
//
//
//    companion object {
//        const val apiKey = ""
//        const val secretKey = ""
//    }
//}