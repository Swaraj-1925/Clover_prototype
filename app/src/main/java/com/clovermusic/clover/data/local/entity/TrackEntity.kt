package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlist_track")
data class TrackEntity(
    @PrimaryKey
    val trackId: String,
    val albumId: String,
    val uri: String,
    val durationMs: Int,
    val name: String,
    val imageUrl: String,
    val previewUrl: String?,
    val timestamp: Long
)
