package com.clovermusic.clover.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.UserEntity

data class UserWithPlaylists(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )

    val playlists: List<PlaylistInfoEntity>
)
