package com.clovermusic.clover.di

import android.content.Context
import com.clovermusic.clover.data.repository.SpotifyAuthRepositoryImpl
import com.clovermusic.clover.domain.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.SpotifyTokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideSpotifyTokenManager(context: Context): SpotifyTokenManager {
        return SpotifyTokenManager(context)
    }

    @Provides
    @Singleton
    fun provideSpotifyAuthRepository(
        tokenManager: SpotifyTokenManager,
        context: Context
    ): SpotifyAuthRepository {
        return SpotifyAuthRepositoryImpl(tokenManager, context)
    }

}