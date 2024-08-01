package com.clovermusic.clover.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.crossRef.PlaylistTrackCrossRef
import com.clovermusic.clover.data.local.entity.PlaylistItemsEntity
import com.clovermusic.clover.data.local.entity.UserPlaylistEntity

data class PlaylistWithTracks(
    @Embedded val playlist: UserPlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackCrossRef::class)
    )
    val tracks: List<PlaylistItemsEntity>
)