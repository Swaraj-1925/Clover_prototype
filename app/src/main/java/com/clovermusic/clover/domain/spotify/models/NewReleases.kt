package com.clovermusic.clover.domain.spotify.models

data class NewReleases(
    val artistsId: String,
    val artistsName: String,
    val id: String,
    val images: String,
    val name: String,
    val release_date: String,
    val total_tracks: Int,
    val type: String,
)
