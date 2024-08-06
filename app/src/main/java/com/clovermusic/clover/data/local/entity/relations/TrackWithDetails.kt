package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.CollaboratorsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.TrackArtistsCrossRef


data class TrackWithDetails(
    @Embedded val track: TrackEntity,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "albumId"
    )
    val album: AlbumEntity,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "artistId",
        associateBy = Junction(TrackArtistsCrossRef::class)
    )
    val artists: List<ArtistsEntity>,
    @Relation(
        parentColumn = "trackId",
        entityColumn = "collaboratorId",
        associateBy = Junction(CollaboratorsTrackCrossRef::class)
    )
    val collaborators: List<CollaboratorsEntity>
)