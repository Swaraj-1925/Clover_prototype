package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey val uri: String,
    val collaborative: Boolean,
    val description: String?,
    val name: String,
    val primaryColor: String?,
    val isPublic: Boolean,
    val snapshotId: String?,
    val totalTrack: Int,
    val imageUrl: String?,
    val timestamp: Long
) {

}
