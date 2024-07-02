package com.clovermusic.clover.data.spotify.models.user.userPlaylists

data class Item(
    val collaborative: Boolean,
    val description: String,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: Tracks,
    val type: String,
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int
)

data class Owner(
    val display_name: String,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)

data class Tracks(
    val href: String,
    val total: Int
)
