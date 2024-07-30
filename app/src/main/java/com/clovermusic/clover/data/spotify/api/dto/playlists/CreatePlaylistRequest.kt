package com.clovermusic.clover.data.spotify.api.dto.playlists

data class CreatePlaylistRequest(
    val name: String,
    val description: String? = null,
    val public: Boolean = true
)
