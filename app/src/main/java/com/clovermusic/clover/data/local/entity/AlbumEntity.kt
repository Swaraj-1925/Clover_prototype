package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey
    val albumId: String,
    val artistId: String,
    val uri: String,
    val name: String,
    val imageUrl: String,
    val releaseDate: String,
    val timestamp: Long
)
