package com.vrashkov.bitfinexcrypto.api

import retrofit2.http.*


interface Api {

    @GET("https://api-pub.bitfinex.com/v2/conf/pub:map:currency:label")
    suspend fun currencies(): List<List<List<String>>>

    @GET("https://api-pub.bitfinex.com/v2/tickers")
    suspend fun currenciesDetails(@Query("symbols") symbols: String): List<List<String?>>
}
