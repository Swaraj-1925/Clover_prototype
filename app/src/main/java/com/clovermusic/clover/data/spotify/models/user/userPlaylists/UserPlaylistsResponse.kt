package com.clovermusic.clover.data.spotify.models.user.userPlaylists

data class UserPlaylistsResponse(
    val items: List<Item>,
    val offset: Int,
    val total: Int
)