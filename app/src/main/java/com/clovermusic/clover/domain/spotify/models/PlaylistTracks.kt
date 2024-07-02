package com.clovermusic.clover.domain.spotify.models

data class PlaylistTracks(
    val artistId: String,
    val artistName: List<String>,
    val imageUrl: String,
    val trackId: String,
    val trackUri: String,
    val trackName: String,
)
