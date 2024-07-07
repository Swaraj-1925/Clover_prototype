package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.playlists.UsersPlaylistItemDto
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.mapper.Util.toOwner
import com.clovermusic.clover.domain.model.Playlist

fun List<UsersPlaylistItemDto>.toPlaylist(): List<Playlist> {
    return map { apiItem ->
        Playlist(
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