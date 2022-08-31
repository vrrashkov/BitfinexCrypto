package com.vrashkov.bitfinexcrypto.repository

import com.vrashkov.bitfinexcrypto.api.Api
import com.vrashkov.bitfinexcrypto.models.RequestResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesDetailsResult
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesResult

import javax.inject.Inject

class GeneralRepository @Inject constructor(
    private val apiService: Api
){

    suspend fun currencies(): RequestResult<CurrenciesResult> = try {
        val request = apiService.currencies()

        if (request.isNotEmpty()) {
            val currenciesDetails: MutableList<CurrenciesResult.Details> = mutableListOf()
            val flattenRequest = request.flatten()
            flattenRequest.forEach { data ->
                currenciesDetails.add(
                    CurrenciesResult.Details(
                        symbol = data.get(0),
                        label = data.get(1),
                        symbolWithPrefix = "t"+data.get(0)+"USD"
                    )
                )
            }

            val currencies = CurrenciesResult(
                symbols = currenciesDetails
            )
            RequestResult.Success(
                data = currencies
            )
        } else {
            RequestResult.Error(exception = Exception(" something went wrong "))
        }

    } catch (e: Exception){
        RequestResult.Error(exception = e)
    }


    suspend fun currenciesDetails(symbols: CurrenciesResult): RequestResult<MutableList<CurrenciesDetailsResult>> = try {
        val symbolsList = symbols.symbols.map { it.symbolWithPrefix }.joinToString(",")

        val request = apiService.currenciesDetails(symbols = symbolsList)

        if (request.isNotEmpty()) {

            val listDetailedCurrencies: MutableList<CurrenciesDetailsResult> = mutableListOf()

            request.forEach { data ->
                val symbolWithPrefix = data.get(0)
                val percentageChange = data.get(6)
                val price = data.get(1)
                val symbol = symbols.symbols.first { it.symbolWithPrefix.equals(symbolWithPrefix) }
                listDetailedCurrencies.add(
                    CurrenciesDetailsResult(
                        label = symbol.label,
                        symbol = symbol.symbol,
                        price = price ?: "0",
                        percentageChange = percentageChange ?: "0"
                    )
                )
            }

            RequestResult.Success(
                data = listDetailedCurrencies
            )
        } else {
            RequestResult.Error(exception = Exception(" something went wrong "))
        }

    } catch (e: Exception){
        RequestResult.Error(exception = e)
    }


}