package com.clovermusic.clover.data.api.spotify.response.userResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.TrackArtistResponse

data class TopArtistsResponse(
    val href: String,
    val next: String?,
    val items: List<TrackArtistResponse>,
    val limit: Int,
    val offset: Int,
    val previous: String,
    val total: Int
)

