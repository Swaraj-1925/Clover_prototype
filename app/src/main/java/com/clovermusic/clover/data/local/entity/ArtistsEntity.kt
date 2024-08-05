package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistsEntity(
    @PrimaryKey(autoGenerate = false)
    val artistId: String,
    val uri: String,
    val genres: List<String>,
    val imageUrl: String,
    val name: String,
    val isFollowed: Boolean = false,
    val isTopArtist: Boolean = false,
    val timestamp: Long
)
