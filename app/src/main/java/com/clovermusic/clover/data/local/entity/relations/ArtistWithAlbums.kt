package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity

data class ArtistWithAlbums(
    @Embedded val artist: ArtistsEntity,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "artistId"
    )
    val albums: List<AlbumEntity>
)
