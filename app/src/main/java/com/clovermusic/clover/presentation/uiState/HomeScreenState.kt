package com.clovermusic.clover.presentation.uiState

import com.clovermusic.clover.domain.model.Albums
import com.clovermusic.clover.domain.model.UserPlaylist
import com.clovermusic.clover.domain.model.common.TrackArtists

data class HomeScreenState(
    val followedArtistsAlbums: List<Albums>,
    val currentUsersPlaylists: List<UserPlaylist>,
    val topArtists: List<TrackArtists>
)
