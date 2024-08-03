package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistsEntity(
    @PrimaryKey val uri: String,
    val artistsId: String,
    val imageUrl: String,
    val name: String,
    val isFollowed: Boolean = false,
    val isTopArtist: Boolean = false,
    val timestamp: Long
)
