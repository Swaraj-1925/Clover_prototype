package com.clovermusic.clover.data.api.spotify.response.playlistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.ImageResponse

data class CurrentUsersPlaylistResponse(
    val href: String,
    val items: List<CurrentUsersPlaylistItem>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any,
    val total: Int
)

data class CurrentUsersPlaylistItem(
    val collaborative: Boolean,
    val description: String,
    val href: String,
    val id: String,
    val images: List<ImageResponse>,
    val name: String,
    val owner: CurrentUsersPlaylistOwner,
    val primary_color: String,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: CurrentUsersPlaylistTracks,
    val type: String,
    val uri: String
)

data class CurrentUsersPlaylistTracks(
    val href: String,
    val total: Int
)

data class CurrentUsersPlaylistOwner(
    val display_name: String,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)
