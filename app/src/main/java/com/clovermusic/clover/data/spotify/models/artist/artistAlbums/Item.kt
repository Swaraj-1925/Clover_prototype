package com.clovermusic.clover.data.spotify.models.artist.artistAlbums

data class Item(
    val artists: List<Artist>,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val total_tracks: Int,
    val type: String,
)

data class Artist(
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int
)
