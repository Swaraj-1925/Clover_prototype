package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val email: String,
    val accountType: String,
    val uri: String,
    val image: String,
    val followers: Int = 0,
    val signUpdate: LocalDate = LocalDate.now(),
    val timeStamp: Long
)
