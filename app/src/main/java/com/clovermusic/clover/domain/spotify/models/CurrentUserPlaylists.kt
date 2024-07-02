package com.clovermusic.clover.domain.spotify.models

import com.clovermusic.clover.data.spotify.models.playlist.currentUserPlaylists.Owner

data class CurrentUserPlaylists(
    val collaborative: Boolean,
    val description: String,
    val id: String,
    val images: String,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val tracksUrl: String,
)
