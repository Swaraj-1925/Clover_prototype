package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_artist")
data class TrackArtistEntity(
    @PrimaryKey val id: String,
    val isFollowed: Boolean = false,
    val followers: Int? = 0,
    val genres: List<String>? = emptyList(),
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)
