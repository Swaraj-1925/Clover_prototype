package com.clovermusic.clover.domain.usecase.user

data class UserUseCases(
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
