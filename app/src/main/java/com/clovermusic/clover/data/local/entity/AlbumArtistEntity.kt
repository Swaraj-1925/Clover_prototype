package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album_artist")
data class AlbumArtistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val uri: String
)
