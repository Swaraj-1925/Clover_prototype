package com.clovermusic.clover.di

import com.clovermusic.clover.data.spotify.repository.ArtistRepositoryImpl
import com.clovermusic.clover.data.spotify.repository.PlaylistRepositoryImpl
import com.clovermusic.clover.data.spotify.repository.UserRepositoryImpl
import com.clovermusic.clover.domain.spotify.repository.ArtistRepository
import com.clovermusic.clover.domain.spotify.repository.PlaylistRepository
import com.clovermusic.clover.domain.spotify.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *bind your repository implementations to their interfaces
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsArtistRepository(
        artistRepositoryImpl: ArtistRepositoryImpl
    ): ArtistRepository

    @Binds
    @Singleton
    abstract fun bindsPlaylistRepository(
        playlistRepositoryImpl: PlaylistRepositoryImpl
    ): PlaylistRepository

}