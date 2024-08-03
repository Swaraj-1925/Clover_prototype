package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val uri: String,
    val id: String,
    val name: String,
    val artists: String,
    val artistsUri: String,
    val artistsId: String,
    val imageUrl: String,
    val releaseDate: String,
    val timestamp: Long
)
