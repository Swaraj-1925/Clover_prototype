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
//Class for all the repositories
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    //Return/create UserRepository using user services and authentication
    @Provides
    @Singleton
    fun providesUserRepository(
        userService: UserService,
        spotifyAuthRepository: SpotifyAuthRepository
    ): UserRepository {
        return UserRepository(userService, spotifyAuthRepository)
    }
// Return/create the ArtistRepository using Artist service
    @Provides
    @Singleton
    fun providesArtistRepository(artistService: ArtistService): ArtistRepository {
        return ArtistRepository(artistService)
    }
// Return/create the playlist repository using the playlist service
    @Provides
    @Singleton
    fun providesPlaylistRepository(playlistService: PlaylistService): PlaylistRepository {
        return PlaylistRepository(playlistService)
    }
}