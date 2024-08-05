package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistTrackCrossRef

// helps a single playlist and all the track associated with it
data class PlaylistWIthTrack(
    @Embedded val playlist: PlaylistInfoEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackCrossRef::class)
    )
    val tracks: List<TrackEntity>
)
