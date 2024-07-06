package com.clovermusic.clover.data.api.spotify.response.userResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.CursorsResponse
import com.clovermusic.clover.data.api.spotify.response.util.FollowersResponse
import com.clovermusic.clover.data.api.spotify.response.util.ImageResponse

data class FollowedArtistsResponse(
    val artists: FollowedArtists
)

data class FollowedArtists(
    val cursors: CursorsResponse,
    val href: String,
    val items: List<FollowedArtistsItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val total: Int
)

data class FollowedArtistsItem(
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
