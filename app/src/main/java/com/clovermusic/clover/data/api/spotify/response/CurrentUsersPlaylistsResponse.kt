package com.clovermusic.clover.data.api.spotify.response

data class CurrentUsersPlaylistsResponse(
    val items: List<CurrentUsersPlaylistsResponseItem>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)

data class CurrentUsersPlaylistsResponseItem(
    val collaborative: Boolean,
    val description: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: OwnerRespons,
    val primary_color: String,
    val `public`: Boolean,
    val snapshot_id: String,
    val tracks: TracksRespons,
    val type: String,
    val uri: String
)

data class OwnerRespons(
    val display_name: String,
    val id: String,
    val type: String,
    val uri: String
)

data class TracksRespons(
    val total: Int
)
