package com.clovermusic.clover.data.local.entity.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["trackId", "artistId"])
data class TrackArtistsCrossRef(
    val trackId: String,
    val artistId: String
)
