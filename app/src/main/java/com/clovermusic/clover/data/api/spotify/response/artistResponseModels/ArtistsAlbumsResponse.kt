package com.clovermusic.clover.data.api.spotify.response.artistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.AlbumResponse

data class ArtistsAlbumsResponse(
    val href: String,
    val items: List<AlbumResponse>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any,
    val total: Int
)

