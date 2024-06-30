package com.clovermusic.clover.data.spotify.models.user.followedArtists

data class Artists(
    val items: List<Item>,
    val total: Int
)

data class Item(
    val genres: List<String>,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int
)
