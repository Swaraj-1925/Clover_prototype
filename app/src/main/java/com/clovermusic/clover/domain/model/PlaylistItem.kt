package com.clovermusic.clover.domain.model

import com.clovermusic.clover.domain.model.util.Artists


data class ItemPlaylist(
    val items: List<PlaylistItem>,
    val next: String,
    val total: Int
)

data class PlaylistItem(
    val added_by: AddedBy,
    val track: Track,
)

data class Track(
    val artists: List<Artists>,
    val duration_ms: Int,
    val id: String,
    val name: String,
    val popularity: Int,
    val preview_url: String,
    val track_number: Int,
    val uri: String
)

data class AddedBy(
    val id: String,
    val type: String,
    val uri: String
)
