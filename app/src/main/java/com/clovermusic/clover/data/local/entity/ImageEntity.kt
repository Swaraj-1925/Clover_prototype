package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey val id: String,
    val height: Int?,
    val width: Int?,
    val url: String,
    val type: ImageType
)

enum class ImageType {
    PLAYLIST,
    ALBUM,
    ARTIST
}
