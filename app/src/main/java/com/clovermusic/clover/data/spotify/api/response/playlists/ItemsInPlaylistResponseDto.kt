package com.clovermusic.clover.data.spotify.api.response.playlists

import com.clovermusic.clover.data.spotify.api.response.common.PlaylistTrackResponseDto

data class ItemsInPlaylistResponseDto(
    val href: String,
    val items: List<PlaylistTrackResponseDto>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
