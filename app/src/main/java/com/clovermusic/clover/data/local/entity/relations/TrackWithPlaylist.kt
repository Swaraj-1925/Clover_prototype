package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistTrackCrossRef

data class TrackWithPlaylist(
    @Embedded val track: TrackEntity,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "playlistId",
        associateBy = Junction(PlaylistTrackCrossRef::class)
    )
    val playlist: List<PlaylistTrackCrossRef>
)
