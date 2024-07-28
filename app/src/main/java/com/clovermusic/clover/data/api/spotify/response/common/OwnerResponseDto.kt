package com.clovermusic.clover.data.api.spotify.response.common
// User data
data class OwnerResponseDto(
    val display_name: String,
    val followers: FollowersResponseDto,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
