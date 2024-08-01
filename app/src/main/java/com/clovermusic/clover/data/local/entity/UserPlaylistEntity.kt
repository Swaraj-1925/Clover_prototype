package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class UserPlaylistEntity(
    @PrimaryKey val id: String,
    val collaborative: Boolean,
    val description: String,
    val name: String,
    val snapshotId: String,
    val uri: String,
    val tracks: Int
)
