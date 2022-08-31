package com.vrashkov.bitfinexcrypto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vrashkov.bitfinexcrypto.ui.screens.crypto.CryptoScreenComponent
import com.vrashkov.bitfinexcrypto.ui.theme.bitfinexcryptoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            bitfinexcryptoTheme {
                CryptoScreenComponent()
            }
        }
    }
}
