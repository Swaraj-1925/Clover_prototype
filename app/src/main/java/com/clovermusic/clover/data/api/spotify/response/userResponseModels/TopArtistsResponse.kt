package com.clovermusic.clover.data.api.spotify.response.userResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.FollowersResponse
import com.clovermusic.clover.data.api.spotify.response.util.ImageResponse

data class TopArtistsResponse(
    val href: String,
    val items: List<TopArtistsItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any,
    val total: Int
)

data class TopArtistsItem(
    val followersResponse: FollowersResponse,
    val genres: List<String>,
    val href: String,
    val id: String,
    val image: List<ImageResponse>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)
