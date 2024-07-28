package com.clovermusic.clover.data.api.spotify.response.playlists
// Request to create new playlist
data class CreatePlaylistRequest(
    val name: String,
    val description: String? = null,
    val public: Boolean = true
)
