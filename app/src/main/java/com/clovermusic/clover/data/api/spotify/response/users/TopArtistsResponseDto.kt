package com.clovermusic.clover.data.api.spotify.response.users

import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto

data class TopArtistsResponseDto(
    val href: String,
    val next: String?,
    val items: List<TrackArtistResponseDto>,
    val limit: Int,
    val offset: Int,
    val previous: String,
    val total: Int
)
