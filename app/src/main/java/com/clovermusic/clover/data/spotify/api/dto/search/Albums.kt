package com.clovermusic.clover.data.spotify.api.dto.search

import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto

data class Albums(
    val href: String,
    val items: List<AlbumResponseDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)