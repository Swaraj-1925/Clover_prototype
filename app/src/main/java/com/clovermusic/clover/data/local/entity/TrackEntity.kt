package com.clovermusic.clover.data.local.entity

import androidx.room.Entity

@Entity(tableName = "tracks")
data class TrackEntity(
    val id: String,
    val durationMs: Int,
    val name: String,
    val previewUrl: String?,
    val uri: String,
    val popularity: Int
)
