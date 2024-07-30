package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: String,
    val addedAt: String,
    val addedById: String,
    val addedByUri: String,
    val discNumber: Int,
    val durationMs: Int,
    val name: String,
    val previewUrl: String? = "",
    val trackNumber: Int,
    val uri: String
)
