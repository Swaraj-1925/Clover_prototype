package com.clovermusic.clover.di

import android.content.Context
import com.clovermusic.clover.data.spotify.persistence.TokenManager
import com.clovermusic.clover.data.spotify.repository.AuthRepositoryImpl
import com.clovermusic.clover.domain.spotify.repository.AuthRepository
import com.clovermusic.clover.domain.spotify.useCase.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl() // Provide the actual implementation
    }

   fun provideTokenManager(@ApplicationContext context: Context): TokenManager{
       return TokenManager(context)
   }
    @Provides
    @Singleton
    fun provideAuthUseCase(
        repository: AuthRepository,
        tokenManager: TokenManager
    ): AuthUseCase {
        return AuthUseCase(repository, tokenManager)
    }
}
