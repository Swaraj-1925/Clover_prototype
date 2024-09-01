package com.clovermusic.clover.data.spotify.api.dto.search

import com.clovermusic.clover.data.spotify.api.dto.playlists.UsersPlaylistItemDto

data class Playlists(
    val href: String,
    val items: List<UsersPlaylistItemDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)