package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.crossRef.TrackArtistsCrossRef

data class ArtistsWithTrack(
    @Embedded val artists: ArtistsEntity,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "trackId",
        associateBy = Junction(TrackArtistsCrossRef::class)
    )
    val tracks: List<TrackEntity>
)
