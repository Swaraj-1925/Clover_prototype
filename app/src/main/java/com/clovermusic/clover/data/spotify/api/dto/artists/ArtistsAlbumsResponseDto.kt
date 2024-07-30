package com.clovermusic.clover.data.spotify.api.dto.artists

import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto

data class ArtistsAlbumsResponseDto(
    val href: String,
    val items: List<AlbumResponseDto>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any,
    val total: Int
)
