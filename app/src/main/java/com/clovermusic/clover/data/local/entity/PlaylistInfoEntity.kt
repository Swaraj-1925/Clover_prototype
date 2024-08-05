package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val playlistId: String,
    val userId: String = " ",
    val uri: String,
    val collaborative: Boolean,
    val description: String?,
    val name: String,
    val snapshotId: String?,
    val totalTrack: Int,
    val imageUrl: String?,
    val followers: Int,
    val timestamp: Long
)
