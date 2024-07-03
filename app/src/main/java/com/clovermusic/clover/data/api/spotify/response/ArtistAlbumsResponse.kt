package com.clovermusic.clover.data.api.spotify.response

data class ArtistAlbumsResponse(
    val href: String,
    val items: List<ArtistAlbumsResponseItem>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)

data class ArtistAlbumsResponseItem(
    val album_group: String,
    val album_type: String,
    val artists: List<AlbumsArtistResponse>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)

data class AlbumsArtistResponse(
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

