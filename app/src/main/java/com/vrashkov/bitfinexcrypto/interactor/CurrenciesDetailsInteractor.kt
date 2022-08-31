package com.vrashkov.bitfinexcrypto.interactor

import android.util.Base64
import com.vrashkov.bitfinexcrypto.models.DataState
import com.vrashkov.bitfinexcrypto.models.ProgressBarState
import com.vrashkov.bitfinexcrypto.models.RequestResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesDetailsResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesResult
import com.vrashkov.bitfinexcrypto.repository.GeneralRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrenciesDetailsInteractor @Inject constructor(
    private val generalRepository: GeneralRepository
) {
    fun execute(currencies: CurrenciesResult): Flow<DataState<out List<CurrenciesDetailsResult>>> = flow {

        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

        when (val currenciesDetailsResult = generalRepository.currenciesDetails(currencies)) {
            is RequestResult.Success -> {
                emit(DataState.Data(data = currenciesDetailsResult.data))
            }
            is RequestResult.Error -> {
                emit(DataState.Error(error = currenciesDetailsResult.exception))
            }
        }

    }


}