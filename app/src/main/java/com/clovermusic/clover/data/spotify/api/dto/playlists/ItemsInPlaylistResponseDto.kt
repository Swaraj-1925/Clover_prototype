package com.clovermusic.clover.data.spotify.api.dto.playlists

import com.clovermusic.clover.data.spotify.api.dto.common.PlaylistTrackResponseDto

data class ItemsInPlaylistResponseDto(
    val href: String,
    val items: List<PlaylistTrackResponseDto>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
