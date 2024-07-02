package com.clovermusic.clover.data.spotify.models.playlist.playlistTracks

data class PlaylistTracksResponse(
    val href: String,
    val items: List<PlaylistTracksResponseItem>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)