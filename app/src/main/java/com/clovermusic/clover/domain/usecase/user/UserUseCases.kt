package com.clovermusic.clover.domain.usecase.user

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val checkIfUserFollowsArtist: CheckIfUserFollowsArtistUseCases,
    val checkIfUserFollowsPlaylist: CheckIfUserFollowsPlaylistUseCase,
    val checkIfUserFollowsUsers: CheckIfUserFollowsUsersUseCases,
    val followedArtists: FollowedArtistsUseCase,
    val followsPlaylist: FollowPlaylistUseCase,
    val getCurrentUsersProfile: GetCurrentUsersProfileUseCase,
    val getUsersProfile: GetUsersProfileUseCase,
    val topArtists: TopArtistUseCase,
    val unfollowPlaylist: UnfollowPlaylistUseCase
)
