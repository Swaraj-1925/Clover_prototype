package com.clovermusic.clover.di

import android.content.Context
import com.clovermusic.clover.data.spotify.persistence.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TokenManagerDi {
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context) : TokenManager {
        return TokenManager(context)
    }
}