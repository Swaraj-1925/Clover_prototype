package com.clovermusic.clover.domain.usecase.user

data class UserUseCases(
    val followedArtists: FollowedArtistsUseCase,
    val topArtist: TopArtistUseCase
)
