package com.clovermusic.clover.data.spotify.api.dto.albums

import com.clovermusic.clover.data.spotify.api.dto.common.AlbumArtistResponseDto

data class AlbumTrackItemDto(
    val artists: List<AlbumArtistResponseDto>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val is_playable: Boolean,
    val linked_from: LinkedFrom,
    val name: String,
    val preview_url: String,
    val track_number: Int,
    val type: String,
    val uri: String
)