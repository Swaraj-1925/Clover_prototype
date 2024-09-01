package com.clovermusic.clover.data.spotify.api.dto.search

import com.clovermusic.clover.data.spotify.api.dto.common.TrackItemsResponseDto

data class TracksX(
    val href: String,
    val items: List<TrackItemsResponseDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)