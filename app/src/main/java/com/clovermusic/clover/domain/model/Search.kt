package com.clovermusic.clover.domain.model

import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity

data class Search(
    val track: List<TrackEntity>? = emptyList(),
    val album: List<AlbumEntity>? = emptyList(),
    val artist: List<ArtistsEntity>? = emptyList(),
    val playlist: List<PlaylistInfoEntity>? = emptyList(),
    val timeStamp: Long = System.currentTimeMillis()
)
