package com.clovermusic.clover.data.api.spotify.response.artistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.ArtistResponse
import com.clovermusic.clover.data.api.spotify.response.util.ImageResponse

data class ArtistsAlbumsResponse(
    val href: String,
    val items: List<ArtistsAlbumsItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any,
    val total: Int
)

data class ArtistsAlbumsItem(
    val album_group: String,
    val album_type: String,
    val artists: List<ArtistResponse>,
    val href: String,
    val id: String,
    val images: List<ImageResponse>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)
