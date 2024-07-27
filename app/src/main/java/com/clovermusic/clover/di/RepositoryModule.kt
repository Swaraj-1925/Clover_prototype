package com.clovermusic.clover.di

/*
import com.clovermusic.clover.data.spotify.api.repository.ArtistRepository
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.spotify.api.repository.UserRepository
import com.clovermusic.clover.data.spotify.api.service.ArtistService
import com.clovermusic.clover.data.spotify.api.service.PlaylistService
import com.clovermusic.clover.data.spotify.api.service.UserService
import com.clovermusic.clover.util.SpotifyTokenManager
import dagger.Binds
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
 */