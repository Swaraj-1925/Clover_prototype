package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlist_track")
data class PlaylistTrackEntity(
    @PrimaryKey val uri: String,
    val id: String,
    val playlistId: String,
    val addedByUri: String,
    val addedById: String,
    val artistName: String,
    val artistUri: String,
    val artistId: String,
    val durationMs: Int,
    val name: String,
    val imageUrl: String,
    val previewUrl: String?,
)
