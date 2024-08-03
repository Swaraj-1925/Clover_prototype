package com.clovermusic.clover.presentation.uiState

import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity

data class HomeScreenState(
    val followedArtistsAlbums: List<AlbumEntity>,
    val currentUsersPlaylists: List<PlaylistInfoEntity>,
    val topArtists: List<ArtistsEntity>
)
