package com.clovermusic.clover.data.local.entity.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["collaboratorId", "collaboratorId"])
data class CollaboratorsTrackCrossRef(
    val trackId: String,
    val collaboratorId: String
)
