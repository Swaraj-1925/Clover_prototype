package com.clovermusic.clover.data.spotify.api.dto.albums

data class AlbumTracksDto(
    val href: String,
    var items: List<AlbumTrackItemDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)