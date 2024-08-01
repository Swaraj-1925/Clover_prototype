package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlist_track")
data class PlaylistTrackEntity(
    @PrimaryKey val uri: String,
    val playlistUri: String,
    val addedByUri: String,
    val addedByImageUrl: String,
    val artistName: List<String>,
    val name: String,
    val imageUrl: String,
    val previewUrl: String,
)
