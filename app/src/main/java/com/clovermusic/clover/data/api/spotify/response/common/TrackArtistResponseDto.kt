package com.clovermusic.clover.data.api.spotify.response.common
// Stored Artist info and used in getFollowedArtists() to fetch followed artist
data class TrackArtistResponseDto(
    val followers: FollowersResponseDto?,
    val genres: List<String>?,
    val href: String,
    val id: String,
    val images: List<ImageResponseDto>?,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)
