package com.clovermusic.clover.data.api.spotify.response.util

data class TrackArtistResponse(
    val followers: FollowersResponse,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<ImageResponse>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)
