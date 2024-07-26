package com.clovermusic.clover.data.spotify.api.response.common

data class OwnerResponseDto(
    val display_name: String,
    val followers: FollowersResponseDto,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
