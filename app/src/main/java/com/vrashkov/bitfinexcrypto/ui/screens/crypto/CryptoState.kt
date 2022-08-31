package com.vrashkov.bitfinexcrypto.ui.screens.crypto

import com.vrashkov.bitfinexcrypto.models.ProgressBarState
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesDetailsResult

data class CryptoState (
    val currencies: List<CurrenciesDetailsResult> = listOf(),
    val currenciesFiltered: List<CurrenciesDetailsResult> = listOf(),
    val search: String = "",
    val loadError: String? = null,
    val loadingCurrencies: ProgressBarState = ProgressBarState.Gone
)