package com.clovermusic.clover.data.local.entity.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "trackId"])
data class PlaylistTrackCrossRef(
    val playlistId: String,
    val trackId: String
)
