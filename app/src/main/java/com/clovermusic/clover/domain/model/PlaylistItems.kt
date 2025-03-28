package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.common.TrackArtists

data class PlaylistItems(
    val addedById: String,
    val addedByUri: String,
    val albums: Albums,
    val artists: List<TrackArtists>,
    val durationMs: Int,
    val id: String,
    val name: String,
    val popularity: Int,
    val previewUrl: String?,
    val uri: String
)
