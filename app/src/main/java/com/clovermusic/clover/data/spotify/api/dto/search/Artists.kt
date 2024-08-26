package com.clovermusic.clover.data.spotify.api.dto.search

import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto

data class Artists(
    val href: String,
    val items: List<TrackArtistResponseDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)