package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: String,
    val name: String,
    val email: String,
    val accountType: String,
    val uri: String,
    val image: String,
    val followers: Int = 0,
    val signUpdate: Long,
    val timeStamp: Long
)
