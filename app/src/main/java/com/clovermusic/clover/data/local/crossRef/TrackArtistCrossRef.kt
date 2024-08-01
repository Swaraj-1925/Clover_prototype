package com.clovermusic.clover.data.local.crossRef

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_artist_cross_ref")
data class TrackArtistCrossRef(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val trackId: String,
    val artistId: String
)