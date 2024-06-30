package com.clovermusic.clover.di

import com.clovermusic.clover.domain.spotify.repository.ArtistRepository
import com.clovermusic.clover.domain.spotify.repository.UserRepository
import com.clovermusic.clover.domain.spotify.useCase.ArtistUseCase
import com.clovermusic.clover.domain.spotify.useCase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUserUseCase(
        userRepository: UserRepository,
        artistUseCase: ArtistUseCase
    ): UserUseCase {
        return UserUseCase(userRepository, artistUseCase)
    }

    @Provides
    @Singleton
    fun provideArtistUseCase(
        artistRepository: ArtistRepository
    ): ArtistUseCase {
        return ArtistUseCase(artistRepository)
    }
}
