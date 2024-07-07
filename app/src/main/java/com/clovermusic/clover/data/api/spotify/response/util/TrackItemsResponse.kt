package com.clovermusic.clover.data.api.spotify.response.util

data class TrackItemsResponse(
    val album: AlbumResponse,
    val artists: List<TrackArtistResponse>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val is_playable: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String?,
    val track_number: Int,
    val type: String,
    val uri: String
)
