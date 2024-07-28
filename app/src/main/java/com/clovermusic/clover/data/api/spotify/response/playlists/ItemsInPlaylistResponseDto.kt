package com.clovermusic.clover.data.api.spotify.response.playlists

import com.clovermusic.clover.data.api.spotify.response.common.PlaylistTrackResponseDto
// Stores the track of playlist and passes to getPlaylistItems
data class ItemsInPlaylistResponseDto(
    val href: String,
    val items: List<PlaylistTrackResponseDto>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
