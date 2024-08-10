package com.clovermusic.clover.domain.model.common

data class PlayingTrackDetails(
    val name: String,
    val songUri: String,
    val artist: String,
    val artistUri: String,
    val image: String,
    val artists: List<String>,
    val artistsUri: List<String>,
    val duration: Long,
    val currentPosition: Long,
    val isShuffling: Boolean,
    val repeatMode: Int
)