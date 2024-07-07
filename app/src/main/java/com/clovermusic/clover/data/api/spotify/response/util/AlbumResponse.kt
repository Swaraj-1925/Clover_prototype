package com.clovermusic.clover.data.api.spotify.response.util


data class AlbumResponse(
    val album_type: String,
    val artists: List<AlbumArtistResponse>,
    val available_markets: List<String>,
    val href: String,
    val id: String,
    val images: List<ImageResponse>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)
