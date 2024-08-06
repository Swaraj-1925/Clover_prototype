package com.clovermusic.clover.data.local.entity.crossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.relations.TrackWithDetails


data class PlaylistWithDetails(
    @Embedded val playlist: PlaylistInfoEntity,
    @Relation(
        entity = TrackEntity::class,
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackCrossRef::class)
    )
    val tracks: List<TrackWithDetails>
)