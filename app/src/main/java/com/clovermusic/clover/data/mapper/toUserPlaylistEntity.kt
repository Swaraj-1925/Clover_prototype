package com.clovermusic.clover.data.mapper

import com.clovermusic.clover.data.local.entity.UserPlaylistEntity
import com.clovermusic.clover.data.spotify.api.dto.playlists.UsersPlaylistItemDto

fun List<UsersPlaylistItemDto>.toUserPlaylistEntity(

): List<UserPlaylistEntity> {
    return map { apiItem ->
        UserPlaylistEntity(
            collaborative = apiItem.collaborative,
            description = apiItem.description,
            id = apiItem.id,
            name = apiItem.name,
            snapshotId = apiItem.snapshot_id,
            uri = apiItem.uri,
            tracks = apiItem.tracks.total
        )
    }
}
