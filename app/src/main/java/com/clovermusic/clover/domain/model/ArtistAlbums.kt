package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.common.Image


data class ArtistAlbums(
    val album_type: String,
    val artists: List<AlbumsArtist>,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)

data class AlbumsArtist(
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)
