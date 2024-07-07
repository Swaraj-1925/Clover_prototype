package com.clovermusic.clover.data.api.spotify.response.playlistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.PlaylistTrackResponse

data class ItemsInPlaylistResponse(
    val href: String,
    val items: List<PlaylistTrackResponse>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)




