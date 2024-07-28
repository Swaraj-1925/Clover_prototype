package com.clovermusic.clover.data.api.spotify.response.common
// Declares the variables required for description of the artist and used in AlbumResponseDto.kt for the same.
data class AlbumArtistResponseDto(
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)
