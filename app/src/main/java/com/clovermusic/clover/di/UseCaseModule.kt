package com.clovermusic.clover.di

import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.UserRepository
import com.clovermusic.clover.domain.usecase.artist.ArtistAlbumsUseCase
import com.clovermusic.clover.domain.usecase.playlist.CurrentUsersPlaylistsUseCase
import com.clovermusic.clover.domain.usecase.user.FollowedArtistsUseCase
import com.clovermusic.clover.domain.usecase.user.TopArtistUseCase
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
    fun provideFollowArtistsUseCase(
        userRepository: UserRepository
    ): FollowedArtistsUseCase {
        return FollowedArtistsUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideTopArtistsUseCase(
        userRepository: UserRepository
    ): TopArtistUseCase {
        return TopArtistUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideCurrentUsersPlaylistsUseCase(
        playlistRepository: PlaylistRepository
    ): CurrentUsersPlaylistsUseCase {
        return CurrentUsersPlaylistsUseCase(playlistRepository)
    }

    @Provides
    @Singleton
    fun provideArtistAlbumsUseCase(
        artistRepository: ArtistRepository
    ): ArtistAlbumsUseCase {
        return ArtistAlbumsUseCase(artistRepository)
    }

}