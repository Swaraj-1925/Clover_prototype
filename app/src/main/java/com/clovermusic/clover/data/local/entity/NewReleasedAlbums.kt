package com.clovermusic.clover.data.local.entity

data class NewReleasedAlbums(
    val uri: String,
    val name: String,
    val artists: List<String>,
    val imageUrl: String,
    val releaseDate: String,
)
