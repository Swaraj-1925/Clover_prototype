package com.clovermusic.clover.di

import com.clovermusic.clover.domain.usecase.artist.ArtistAlbumsUseCase
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.domain.usecase.playlist.CurrentUsersPlaylistsUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistItemsUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.user.FollowedArtistsUseCase
import com.clovermusic.clover.domain.usecase.user.TopArtistUseCase
import com.clovermusic.clover.domain.usecase.user.UserUseCases
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
    fun provideUserUseCases(
        followedArtistsUseCase: FollowedArtistsUseCase,
        topArtistUseCase: TopArtistUseCase
    ): UserUseCases {
        return UserUseCases(
            followedArtists = followedArtistsUseCase,
            topArtist = topArtistUseCase
        )
    }

    @Provides
    @Singleton
    fun providePlaylistUseCases(
        playlistItemsUseCase: PlaylistItemsUseCase,
        currentUsersPlaylistsUseCase: CurrentUsersPlaylistsUseCase,
        playlistUseCase: PlaylistUseCase

    ): PlaylistUseCases {
        return PlaylistUseCases(
            playlistItems = playlistItemsUseCase,
            currentUsersPlaylists = currentUsersPlaylistsUseCase,
            playlistUseCase
        )
    }

    @Provides
    @Singleton
    fun provideArtistUseCases(
        artistAlbumsUseCase: ArtistAlbumsUseCase
    ): ArtistUseCases {
        return ArtistUseCases(
            artistAlbums = artistAlbumsUseCase
        )
    }
}