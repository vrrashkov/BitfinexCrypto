package com.vrashkov.bitfinexcrypto.di

import com.squareup.moshi.Moshi
import com.vrashkov.bitfinexcrypto.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {
    protected open fun baseUrl() = "https://api-pub.bitfinex.com/v2/conf/"

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}