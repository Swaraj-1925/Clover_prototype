package com.clovermusic.clover.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collaborators")
data class CollaboratorsEntity(
    @PrimaryKey(autoGenerate = false)
    val collaboratorId: String,
    val name: String = " ",
    val imageUrl: String = " ",
    val timeStamps: Long
)
