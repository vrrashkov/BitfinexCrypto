package com.vrashkov.bitfinexcrypto

import app.cash.turbine.test
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import android.util.Base64
import com.vrashkov.bitfinexcrypto.interactor.CurrenciesDetailsInteractor
import com.vrashkov.bitfinexcrypto.interactor.CurrenciesInteractor
import com.vrashkov.bitfinexcrypto.models.DataState
import com.vrashkov.bitfinexcrypto.models.RequestResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesDetailsResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesResult
import com.vrashkov.bitfinexcrypto.repository.GeneralRepository
import org.junit.Assert.*

class CurrenciesDetailsInteractorTest {

    @MockK
    private lateinit var generalRepository: GeneralRepository

    private lateinit var interactor: CurrenciesDetailsInteractor

    private val symbols = CurrenciesResult(
        listOf(
            CurrenciesResult.Details(
                label = "BTC",
                symbol = "BTC",
                symbolWithPrefix = "tBTCUSD"
            )
        )
    )

    private val successResult = mutableListOf(
            CurrenciesDetailsResult(
                label = "BTC",
                symbol = "BTC",
                price = "0",
                percentageChange = "0"
            )
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun produceUseCase() = CurrenciesDetailsInteractor(
        generalRepository = generalRepository
    )

    @Test
    fun `execute currenciesDetails check if returns data`() = runBlockingTest {

        coEvery {
            generalRepository.currenciesDetails(symbols = symbols)
        } returns RequestResult.Success(successResult)

        interactor = produceUseCase()

        interactor.execute(symbols).test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Data)

            awaitComplete()
        }

        coVerify { generalRepository.currenciesDetails(symbols) }
    }

    @Test
    fun `execute currenciesDetails check if returns error`() = runBlockingTest {
        val fakeException = Exception()
        coEvery {
            generalRepository.currenciesDetails(symbols = symbols)
        } returns RequestResult.Error(fakeException)

        interactor = produceUseCase()

        val flow = interactor.execute(symbols)

        flow.test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Error)
            assertEquals(fakeException, (secondResult as DataState.Error).error)
            awaitComplete()
        }
    }

}