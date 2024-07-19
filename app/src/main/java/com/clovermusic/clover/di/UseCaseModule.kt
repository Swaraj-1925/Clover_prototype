package com.clovermusic.clover.di

import com.clovermusic.clover.domain.usecase.artist.ArtistAlbumsUseCase
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.domain.usecase.artist.ArtistsTopTracksUseCase
import com.clovermusic.clover.domain.usecase.artist.GetArtistRelatedArtistsUseCase
import com.clovermusic.clover.domain.usecase.playlist.AddItemsToPlaylistUseCase
import com.clovermusic.clover.domain.usecase.playlist.CreateNewPlaylistUseCase
import com.clovermusic.clover.domain.usecase.playlist.CurrentUsersPlaylistsUseCase
import com.clovermusic.clover.domain.usecase.playlist.GetPlaylistItemsUseCase
import com.clovermusic.clover.domain.usecase.playlist.GetPlaylistUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.playlist.RemovePlaylistItemsUseCase
import com.clovermusic.clover.domain.usecase.playlist.UploadPlaylistCoverUseCase
import com.clovermusic.clover.domain.usecase.user.CheckIfUserFollowsArtistUseCases
import com.clovermusic.clover.domain.usecase.user.CheckIfUserFollowsPlaylistUseCase
import com.clovermusic.clover.domain.usecase.user.CheckIfUserFollowsUsersUseCases
import com.clovermusic.clover.domain.usecase.user.FollowPlaylistUseCase
import com.clovermusic.clover.domain.usecase.user.FollowedArtistsUseCase
import com.clovermusic.clover.domain.usecase.user.GetCurrentUsersProfileUseCase
import com.clovermusic.clover.domain.usecase.user.GetUsersProfileUseCase
import com.clovermusic.clover.domain.usecase.user.TopArtistUseCase
import com.clovermusic.clover.domain.usecase.user.UnfollowPlaylistUseCase
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
        checkIfUserFollowsArtistUseCases: CheckIfUserFollowsArtistUseCases,
        checkIfUserFollowsPlaylistUseCase: CheckIfUserFollowsPlaylistUseCase,
        checkIfUserFollowsUsersUseCases: CheckIfUserFollowsUsersUseCases,
        followedArtistsUseCase: FollowedArtistsUseCase,
        followsPlaylistUseCase: FollowPlaylistUseCase,
        getCurrentUsersProfileUseCase: GetCurrentUsersProfileUseCase,
        getUsersProfileUseCase: GetUsersProfileUseCase,
        topArtistsUseCase: TopArtistUseCase,
        unfollowPlaylistUseCase: UnfollowPlaylistUseCase
    ): UserUseCases {
        return UserUseCases(
            checkIfUserFollowsArtist = checkIfUserFollowsArtistUseCases,
            checkIfUserFollowsPlaylist = checkIfUserFollowsPlaylistUseCase,
            checkIfUserFollowsUsers = checkIfUserFollowsUsersUseCases,
            followedArtists = followedArtistsUseCase,
            followsPlaylist = followsPlaylistUseCase,
            getCurrentUsersProfile = getCurrentUsersProfileUseCase,
            getUsersProfile = getUsersProfileUseCase,
            topArtists = topArtistsUseCase,
            unfollowPlaylist = unfollowPlaylistUseCase
        )
    }

    @Provides
    @Singleton
    fun providePlaylistUseCases(
        addItemsToPlaylistUseCase: AddItemsToPlaylistUseCase,
        createNewPlaylistUseCase: CreateNewPlaylistUseCase,
        getPlaylistItemsUseCase: GetPlaylistItemsUseCase,
        getPlaylistUseCase: GetPlaylistUseCase,
        removePlaylistItemsUseCase: RemovePlaylistItemsUseCase,
        uploadPlaylistCoverUseCase: UploadPlaylistCoverUseCase,
        currentUserPlaylistUseCases: CurrentUsersPlaylistsUseCase
    ): PlaylistUseCases {
        return PlaylistUseCases(
            addItemsToPlaylist = addItemsToPlaylistUseCase,
            createNewPlaylist = createNewPlaylistUseCase,
            getPlaylistItems = getPlaylistItemsUseCase,
            getPlaylist = getPlaylistUseCase,
            removePlaylistItems = removePlaylistItemsUseCase,
            uploadPlaylistCover = uploadPlaylistCoverUseCase,
            currentUserPlaylist = currentUserPlaylistUseCases
        )
    }

    @Provides
    @Singleton
    fun provideArtistUseCases(
        artistAlbumsUseCase: ArtistAlbumsUseCase,
        artistsTopTracksUseCase: ArtistsTopTracksUseCase,
        getArtistRelatedArtistsUseCase: GetArtistRelatedArtistsUseCase
    ): ArtistUseCases {
        return ArtistUseCases(
            artistAlbums = artistAlbumsUseCase,
            artistTopTrack = artistsTopTracksUseCase,
            getArtistRelatedArtists = getArtistRelatedArtistsUseCase
        )
    }
}