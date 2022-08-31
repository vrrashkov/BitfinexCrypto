package com.vrashkov.bitfinexcrypto.ui.screens.crypto

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vrashkov.bitfinexcrypto.interactor.CurrenciesDetailsInteractor
import com.vrashkov.bitfinexcrypto.interactor.CurrenciesInteractor
import com.vrashkov.bitfinexcrypto.models.DataState
import com.vrashkov.bitfinexcrypto.models.ProgressBarState
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class CryptoVM @Inject constructor(
    private val currenciesInteractor: CurrenciesInteractor,
    private val currenciesDetailsInteractor: CurrenciesDetailsInteractor
) : ViewModel() {

    var viewState: MutableState<CryptoState> = mutableStateOf(CryptoState())

    var currenciesList: CurrenciesResult? = null

    init {
        viewModelScope.launch {
            currenciesInteractor.execute().collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        if (viewState.value.currencies.isEmpty()) {
                            viewState.value = viewState.value
                                .copy(
                                    loadingCurrencies = ProgressBarState.Loading
                                )
                        }
                    }
                    is DataState.Data -> {
                        println(result.data)
                        currenciesList = result.data
                    }
                    is DataState.Error -> {
                        viewState.value = viewState.value
                            .copy(
                                loadError = "Could not retrieve currencies"
                            )
                    }
                }
            }
        }
    }

    fun onTriggerEvent(event: CryptoEvent) {
        when (event) {
            CryptoEvent.ReloadCurrenicesDetailsData -> {
                currenciesList?.let { currencies ->
                    viewModelScope.launch {
                        currenciesDetailsInteractor.execute(currencies).collect { result ->
                            when (result) {
                                is DataState.Loading -> {
                                }
                                is DataState.Data -> {
                                    val currencies = result.data!!
                                    if (viewState.value.search.isEmpty()) {
                                        viewState.value = viewState.value
                                            .copy(
                                                currencies = currencies,
                                                currenciesFiltered = currencies,
                                                loadError = null,
                                                loadingCurrencies = ProgressBarState.Gone
                                            )
                                    } else {
                                        val filtered = currencies.filter { it.label.lowercase().contains(viewState.value.search.lowercase()) }
                                        viewState.value = viewState.value
                                            .copy(
                                                currencies = currencies,
                                                currenciesFiltered = filtered,
                                                loadError = null,
                                                loadingCurrencies = ProgressBarState.Gone
                                            )
                                    }

                                }
                                is DataState.Error -> {
                                    viewState.value = viewState.value
                                        .copy(
                                            loadError = "Could not load results"
                                        )
                                }
                            }
                        }
                    }
                }
            }
            is CryptoEvent.OnSearchValueChange -> {
                if (viewState.value.search.isEmpty()) {
                    viewState.value = viewState.value
                        .copy(
                            search = event.newValue,
                            currenciesFiltered = viewState.value.currencies
                        )
                } else {
                    val filtered = viewState.value.currencies.filter { it.label.lowercase().contains(event.newValue.lowercase()) }
                    viewState.value = viewState.value
                        .copy(
                            search = event.newValue,
                            currenciesFiltered = filtered
                        )
                }
            }
        }
    }
}