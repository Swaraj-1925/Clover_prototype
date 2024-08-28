package com.clovermusic.clover.data.spotify.api.dto.common

data class TrackArtistResponseDto(
    val followers: FollowersResponseDto?,
    val genres: List<String>?,
    val href: String,
    val id: String,
    val images: List<ImageResponseDto>?,
    val name: String?,
    val popularity: Int? ,
    val type: String,
    val uri: String
)
