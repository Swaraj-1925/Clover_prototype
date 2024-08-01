package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val height: Int?,
    val width: Int?,
    val ownerId: String,
    val type: ImageType
)

enum class ImageType {
    PLAYLIST,
    ALBUM,
    ARTIST
}
