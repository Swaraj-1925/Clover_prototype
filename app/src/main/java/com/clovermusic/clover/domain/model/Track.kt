package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.common.TrackArtists

data class Track(
    val artists: List<TrackArtists>,
    val durationMs: Int,
    val id: String,
    val name: String,
    val previewUrl: String?,
    val uri: String,
    val popularity: Int
)
