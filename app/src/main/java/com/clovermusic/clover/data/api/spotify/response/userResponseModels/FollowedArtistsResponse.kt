package com.clovermusic.clover.data.api.spotify.response.userResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.CursorsResponse
import com.clovermusic.clover.data.api.spotify.response.util.TrackArtistResponse

data class FollowedArtistsResponse(
    val artists: FollowedArtists
)

data class FollowedArtists(
    val cursors: CursorsResponse,
    val href: String,
    val items: List<TrackArtistResponse>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val total: Int
)


