package com.clovermusic.clover.presentation.uiState

import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.relations.AlbumWithTrack
import com.clovermusic.clover.data.local.entity.relations.ArtistWithAlbums
import com.clovermusic.clover.data.local.entity.relations.TrackWithArtists

data class ArtistDataUiState(
    val artistInfo: ArtistsEntity,
    val artistAlbums: List<ArtistWithAlbums>,
    val artistTopTracks: List<TrackWithArtists>
)
