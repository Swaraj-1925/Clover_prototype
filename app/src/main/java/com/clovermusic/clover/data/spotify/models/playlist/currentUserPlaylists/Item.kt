package com.clovermusic.clover.data.spotify.models.playlist.currentUserPlaylists

data class CurrentUserPlaylistResponseItem(
    val collaborative: Boolean,
    val description: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val tracks: Tracks,
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int
)

data class Owner(
    val display_name: String,
    val id: String,
)

data class Tracks(
    val href: String,
    val total: Int
)
