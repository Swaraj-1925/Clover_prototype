package com.clovermusic.clover.di

import com.clovermusic.clover.domain.spotify.repository.ArtistRepository
import com.clovermusic.clover.domain.spotify.repository.PlaylistRepository
import com.clovermusic.clover.domain.spotify.repository.UserRepository
import com.clovermusic.clover.domain.spotify.useCase.ArtistUseCase
import com.clovermusic.clover.domain.spotify.useCase.PlaylistsUseCase
import com.clovermusic.clover.domain.spotify.useCase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * use cases as singletons and provided them with the necessary repository dependencies.
 */
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

    @Provides
    @Singleton
    fun providePlaylistUseCase(
        playlistRepository: PlaylistRepository
    ): PlaylistsUseCase {
        return PlaylistsUseCase(playlistRepository)
    }
}
