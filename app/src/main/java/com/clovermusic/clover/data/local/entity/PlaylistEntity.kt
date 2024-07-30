package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey val id: String,
    val collaborative: Boolean,
    val description: String,
    val followers: Int? = 0,
    val name: String,
    val snapshotId: String,
    val uri: String
)
