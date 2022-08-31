package com.vrashkov.bitfinexcrypto

import com.vrashkov.bitfinexcrypto.interactor.CurrenciesDetailsInteractor
import com.vrashkov.bitfinexcrypto.interactor.CurrenciesInteractor
import com.vrashkov.bitfinexcrypto.ui.screens.crypto.CryptoEvent
import com.vrashkov.bitfinexcrypto.ui.screens.crypto.CryptoState
import com.vrashkov.bitfinexcrypto.ui.screens.crypto.CryptoVM
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule

class CryptoVMTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var currenciesInteractor: CurrenciesInteractor

    @MockK
    lateinit var currenciesDetailsInteractor: CurrenciesDetailsInteractor

    private lateinit var viewModel: CryptoVM

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun produceViewModel(viewState: CryptoState = CryptoState()): CryptoVM {
        val viewModel = CryptoVM(currenciesInteractor = currenciesInteractor, currenciesDetailsInteractor = currenciesDetailsInteractor)
        viewModel.viewState.value = viewState
        return viewModel
    }

    private fun getCurrentViewState() = viewModel.viewState.value

    @Test
    fun `when OnSearchValueChange is triggered with non-empty value`() {
        val fakeValue = "123"
        viewModel = produceViewModel(
            viewState = CryptoState()
        )

        viewModel.onTriggerEvent(CryptoEvent.OnSearchValueChange(newValue = fakeValue))

        assertEquals(fakeValue, getCurrentViewState().search)
    }

}