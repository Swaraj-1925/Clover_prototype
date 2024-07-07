package com.clovermusic.clover.data.api.spotify.response.util


data class PlaylistTrackResponse(
    val added_at: String,
    val added_by: AddedByResponse,
    val is_local: Boolean,
    val track: TrackItemsResponse
)
