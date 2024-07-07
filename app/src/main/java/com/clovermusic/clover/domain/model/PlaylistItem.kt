package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.util.Artists
import com.clovermusic.clover.domain.model.util.Image


data class PlaylistItem(
    val addedById: String,
    val addedByUri: String,
    val artists: List<Artists>,
    val image: List<Image>,
    val trackDurationMs: Int,
    val trackId: String,
    val trackName: String,
    val trackPopularity: Int,
    val trackPreviewUrl: String?,
    val trackNumber: Int,
    val trackUri: String,
)
