package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistInfoEntity(
    @PrimaryKey
    val playlistId: String,
    val uri: String,
    val collaborative: Boolean,
    val description: String?,
    val name: String,
    val snapshotId: String?,
    val totalTrack: Int,
    val owner: String,
    val ownerId: String,
    val imageUrl: String?,
    val followers: Int = 0,
    val numClick: Int = 0,
    val timestamp: Long
)
