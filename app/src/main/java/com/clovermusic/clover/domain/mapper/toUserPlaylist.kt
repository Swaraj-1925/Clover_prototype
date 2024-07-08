package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.playlists.UsersPlaylistItemDto
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.mapper.Util.toOwner
import com.clovermusic.clover.domain.model.UserPlaylist

fun List<UsersPlaylistItemDto>.toUserPlaylist(): List<UserPlaylist> {
    return map { apiItem ->
        UserPlaylist(
            collaborative = apiItem.collaborative,
            description = apiItem.description,
            id = apiItem.id,
            image = apiItem.images.toImages(),
            name = apiItem.name,
            snapshotId = apiItem.snapshot_id,
            owner = apiItem.owner.toOwner(),
            uri = apiItem.uri,
            tracks = apiItem.tracks.total
        )
    }
}