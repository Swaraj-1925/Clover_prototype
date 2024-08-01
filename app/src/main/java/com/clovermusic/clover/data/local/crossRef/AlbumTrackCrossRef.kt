package com.clovermusic.clover.data.local.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["albumId", "trackId"])
data class AlbumTrackCrossRef(
    val albumId: String,
    val trackId: String
)
