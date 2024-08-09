package com.clovermusic.clover.data.spotify.api.dto.common

data class AlbumResponseDto(
    val album_type: String,
    val artists: List<AlbumArtistResponseDto>,
    val available_markets: List<String>,
    val href: String,
    val id: String,
    val images: List<ImageResponseDto?>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)
