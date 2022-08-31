package com.vrashkov.bitfinexcrypto.interactor

import android.util.Base64
import com.vrashkov.bitfinexcrypto.models.DataState
import com.vrashkov.bitfinexcrypto.models.ProgressBarState
import com.vrashkov.bitfinexcrypto.models.RequestResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesResult
import com.vrashkov.bitfinexcrypto.repository.GeneralRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrenciesInteractor @Inject constructor(
    private val generalRepository: GeneralRepository
) {
    fun execute(): Flow<DataState<out CurrenciesResult>> = flow {

        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

        val currenciesResult = generalRepository.currencies()
        when (currenciesResult) {
            is RequestResult.Success -> {
                emit(DataState.Data(data = currenciesResult.data))
            }
            is RequestResult.Error -> {
                emit(DataState.Error(error = currenciesResult.exception))
            }
        }
    }


}