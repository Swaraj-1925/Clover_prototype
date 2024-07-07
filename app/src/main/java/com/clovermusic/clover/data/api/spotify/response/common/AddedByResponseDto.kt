package com.clovermusic.clover.data.api.spotify.response.common

data class AddedByResponseDto(
    val followers: FollowersResponseDto,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
