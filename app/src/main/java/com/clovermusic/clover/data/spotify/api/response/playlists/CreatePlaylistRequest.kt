package com.clovermusic.clover.data.spotify.api.response.playlists

data class CreatePlaylistRequest(
    val name: String,
    val description: String? = null,
    val public: Boolean = true
)
