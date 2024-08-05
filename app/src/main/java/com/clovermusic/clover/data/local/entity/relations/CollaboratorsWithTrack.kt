package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.CollaboratorsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef

data class CollaboratorsWithTrack(
    @Embedded val collaborators: CollaboratorsEntity,
    @Relation(
        parentColumn = "collaboratorId",
        entityColumn = "trackId",
        associateBy = Junction(CollaboratorsTrackCrossRef::class)
    )
    val track: List<TrackEntity>
)
