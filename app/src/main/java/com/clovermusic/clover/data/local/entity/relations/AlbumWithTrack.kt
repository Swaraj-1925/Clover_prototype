package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.TrackEntity

data class AlbumWithTrack(
    @Embedded val album: AlbumEntity,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "albumId"
    )
    val tracks: List<TrackEntity>
)
