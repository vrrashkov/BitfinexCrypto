package com.vrashkov.bitfinexcrypto.ui.screens.crypto

sealed class CryptoEvent {
    object ReloadCurrenicesDetailsData: CryptoEvent()

    data class OnSearchValueChange(val newValue: String): CryptoEvent()
}