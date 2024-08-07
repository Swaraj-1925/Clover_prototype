package com.clovermusic.clover.data.spotify.api.dto.common

data class PlaylistTrackResponseDto(
    val added_at: String,
    val added_by: AddedByResponseDto,
    val is_local: Boolean,
    val track: TrackItemsResponseDto
)
