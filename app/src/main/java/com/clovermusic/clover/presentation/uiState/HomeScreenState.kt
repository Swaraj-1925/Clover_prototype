package com.clovermusic.clover.presentation.uiState

import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.relations.ArtistWithAlbums
import com.clovermusic.clover.util.DataState

data class HomeScreenState(
    val followedArtistsAlbums: DataState<List<ArtistWithAlbums>> = DataState.Loading,
    val currentUsersPlaylists: DataState<List<PlaylistInfoEntity>> = DataState.Loading,
    val topArtists: DataState<List<ArtistsEntity>> = DataState.Loading
)
