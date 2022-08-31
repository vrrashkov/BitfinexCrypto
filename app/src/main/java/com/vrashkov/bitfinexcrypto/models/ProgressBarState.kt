package com.vrashkov.bitfinexcrypto.models

sealed class ProgressBarState{
    
    object Loading: ProgressBarState()
    
    object Gone: ProgressBarState()
}