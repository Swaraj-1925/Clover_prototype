package com.clovermusic.clover.data.local.crossRef

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_track_cross_ref")
data class PlaylistTrackCrossRef(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playlistId: String,
    val trackId: String,
    val addedAt: String,
    val addedById: String,
    val addedByUri: String
)
