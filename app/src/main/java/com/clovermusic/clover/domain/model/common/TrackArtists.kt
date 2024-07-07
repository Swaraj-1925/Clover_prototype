package com.clovermusic.clover.domain.model.common

data class TrackArtists(
    val followers: Int?,
    val genres: List<String>?,
    val id: String,
    val images: List<Image>?,
    val name: String,
    val popularity: Int,
    val uri: String
)
