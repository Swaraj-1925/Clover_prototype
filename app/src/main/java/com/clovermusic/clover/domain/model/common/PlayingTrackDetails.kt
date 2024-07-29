package com.clovermusic.clover.domain.model.common

data class PlayingTrackDetails(
    val name: String,
    val songUri: String,
    val artists: String,
    val artistsUri: String,
    val image: String
)
