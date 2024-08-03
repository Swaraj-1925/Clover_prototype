package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistInfoEntity(
    @PrimaryKey val uri: String,
    val id: String,
    val collaborative: Boolean,
    val description: String?,
    val name: String,
    val primaryColor: String?,
    val owner: String,
    val ownerId: String,
    val isPublic: Boolean,
    val snapshotId: String?,
    val totalTrack: Int,
    val imageUrl: String?,
    val followers: Int,
    val timestamp: Long
)
