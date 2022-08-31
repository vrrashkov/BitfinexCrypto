package com.vrashkov.bitfinexcrypto.models.mapped

data class CurrenciesResult (
    val symbols: List<Details>
) {
    data class Details (
        val label: String,
        val symbol: String,
        val symbolWithPrefix: String
    )
}
