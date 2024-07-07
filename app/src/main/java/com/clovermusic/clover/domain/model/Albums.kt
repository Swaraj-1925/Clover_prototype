package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.common.AlbumArtist
import com.clovermusic.clover.domain.model.common.Image

data class Albums(
    val artists: List<AlbumArtist>,
    val totalTracks: Int,
    val albumId: String,
    val albumName: String,
    val albumImage: List<Image>,
    val releaseDate: String,
    val uri: String
)
