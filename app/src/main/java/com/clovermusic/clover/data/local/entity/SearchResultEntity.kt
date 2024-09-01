package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "search_result")
data class SearchResultEntity (
    @PrimaryKey
    val itemId: String,
    val type: String,
    val name: String,
    val uri: String,
    val imageUrl: String,
    val timestamp: Long = System.currentTimeMillis()
)