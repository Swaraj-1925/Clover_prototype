package com.clovermusic.clover.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.ImageEntity
import com.clovermusic.clover.data.local.entity.UserPlaylistEntity

data class PlaylistWithImage(
    @Embedded val playlist: UserPlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "playlistId"
    )
    val image: ImageEntity
)
