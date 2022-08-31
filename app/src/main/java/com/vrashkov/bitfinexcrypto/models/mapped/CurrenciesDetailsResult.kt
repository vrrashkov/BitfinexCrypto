package com.vrashkov.bitfinexcrypto.models.mapped

data class CurrenciesDetailsResult (
    val label: String,
    val symbol: String,
    var price: String,
    val percentageChange: String
)