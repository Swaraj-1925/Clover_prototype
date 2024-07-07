package com.clovermusic.clover.di

import com.clovermusic.clover.domain.usecase.artist.ArtistAlbumsUseCase
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.domain.usecase.artist.ArtistsTopTracksUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistItemsUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.playlist.UsersPlaylistsUseCase
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
        usersPlaylistsUseCase: UsersPlaylistsUseCase,
        playlistUseCase: PlaylistUseCase

    ): PlaylistUseCases {
        return PlaylistUseCases(
            playlistItems = playlistItemsUseCase,
            currentUsersPlaylists = usersPlaylistsUseCase,
            playlistUseCase
        )
    }

    @Provides
    @Singleton
    fun provideArtistUseCases(
        artistAlbumsUseCase: ArtistAlbumsUseCase,
        artistsTopTracksUseCase: ArtistsTopTracksUseCase
    ): ArtistUseCases {
        return ArtistUseCases(
            artistAlbums = artistAlbumsUseCase,
            artistTopTrack = artistsTopTracksUseCase
        )
    }
}