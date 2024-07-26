package com.clovermusic.clover.data.spotify.api.response.users

import com.clovermusic.clover.data.spotify.api.response.common.TrackArtistResponseDto

data class TopArtistsResponseDto(
    val href: String,
    val next: String?,
    val items: List<TrackArtistResponseDto>,
    val limit: Int,
    val offset: Int,
    val previous: String,
    val total: Int
)
