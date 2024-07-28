package com.clovermusic.clover.data.api.spotify.response.common
// Used in PlaylistTrackResponseDto.kt which is implemented in PlaylistItems.kt to store playlist details.
data class AddedByResponseDto(
    val followers: FollowersResponseDto,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
