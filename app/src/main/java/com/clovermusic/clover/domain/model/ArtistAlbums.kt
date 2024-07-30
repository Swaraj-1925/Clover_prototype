package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.common.Image


data class ArtistAlbums(
    val albumType: String,
    val artists: List<AlbumsArtist>,
    val id: String,
    val images: List<Image>,
    val name: String,
    val releaseDate: String,
    val totalTracks: Int,
    val type: String,
    val uri: String
)


