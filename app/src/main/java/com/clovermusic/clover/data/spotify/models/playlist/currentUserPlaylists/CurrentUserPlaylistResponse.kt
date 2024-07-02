package com.clovermusic.clover.data.spotify.models.playlist.currentUserPlaylists

data class CurrentUserPlaylistResponse(
    val items: List<CurrentUserPlaylistResponseItem>,
    val total: Int
)