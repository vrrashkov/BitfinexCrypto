package com.vrashkov.bitfinexcrypto

import app.cash.turbine.test
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import android.util.Base64
import com.vrashkov.bitfinexcrypto.interactor.CurrenciesInteractor
import com.vrashkov.bitfinexcrypto.models.DataState
import com.vrashkov.bitfinexcrypto.models.RequestResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesResult
import com.vrashkov.bitfinexcrypto.repository.GeneralRepository
import org.junit.Assert.*

class CurrenciesInteractorTest {

    @MockK
    private lateinit var generalRepository: GeneralRepository

    private lateinit var interactor: CurrenciesInteractor

    private val successResult = CurrenciesResult(
        listOf(
            CurrenciesResult.Details(
                label = "BTC",
                symbol = "BTC",
                symbolWithPrefix = "tBTCUSD"
            )
        )
    )
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun produceUseCase() = CurrenciesInteractor(
        generalRepository = generalRepository
    )

    @Test
    fun `execute currenceis check if returns data`() = runBlockingTest {

        coEvery {
            generalRepository.currencies()
        } returns RequestResult.Success(successResult)

        interactor = produceUseCase()

        interactor.execute().test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Data)

            awaitComplete()
        }

        coVerify { generalRepository.currencies() }
    }

    @Test
    fun `execute currenceis check if returns error`() = runBlockingTest {
        val fakeException = Exception()
        coEvery {
            generalRepository.currencies()
        } returns RequestResult.Error(fakeException)

        interactor = produceUseCase()

        val flow = interactor.execute()

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