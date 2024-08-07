package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.CollaboratorsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef

data class TrackWithCollaborators(
    @Embedded val track: TrackEntity,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "collaboratorId",
        associateBy = Junction(CollaboratorsTrackCrossRef::class)
    )
    val collaborators: List<CollaboratorsEntity>
)
