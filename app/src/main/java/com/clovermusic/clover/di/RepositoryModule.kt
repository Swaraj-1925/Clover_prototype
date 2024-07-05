package com.clovermusic.clover.di

import com.clovermusic.clover.data.api.spotify.service.ArtistService
import com.clovermusic.clover.data.api.spotify.service.PlaylistService
import com.clovermusic.clover.data.api.spotify.service.UserService
import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesUserRepository(
        userService: UserService,
        spotifyAuthRepository: SpotifyAuthRepository
    ): UserRepository {
        return UserRepository(userService, spotifyAuthRepository)
    }

    @Provides
    @Singleton
    fun providesArtistRepository(artistService: ArtistService): ArtistRepository {
        return ArtistRepository(artistService)
    }

    @Provides
    @Singleton
    fun providesPlaylistRepository(playlistService: PlaylistService): PlaylistRepository {
        return PlaylistRepository(playlistService)
    }
}