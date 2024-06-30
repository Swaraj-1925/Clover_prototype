package com.clovermusic.clover.data.spotify.models.user.topItems

data class TopArtistsResponse(
    val items: List<Item>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)

data class Item(
    val genres: List<String>,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int
)


