package com.clovermusic.clover.data.api.spotify.response

data class TopArtistResponse(
    val href: String,
    val items: List<ArtistResponseItem>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)

