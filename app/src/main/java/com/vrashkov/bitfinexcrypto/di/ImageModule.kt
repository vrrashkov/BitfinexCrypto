package com.vrashkov.bitfinexcrypto.di

import android.app.Application
import coil.ImageLoader
import com.vrashkov.bitfinexcrypto.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ImageModule {

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .crossfade(true)
            .build()
    }
}

