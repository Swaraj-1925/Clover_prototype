package com.clovermusic.clover.data.local.entity

import androidx.room.Entity

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    val country: String,
    val displayName: String,
    val email: String,
    val followers: Int,
    val image: List<ImageEntity>,
    val product: String? = "",
    val type: String,
    val uri: String
)
