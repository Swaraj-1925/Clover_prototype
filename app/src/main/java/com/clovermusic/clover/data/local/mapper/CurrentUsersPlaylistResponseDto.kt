package com.clovermusic.clover.data.local.mapper

import com.clovermusic.clover.data.local.entity.PlaylistEntity
import com.clovermusic.clover.data.spotify.api.dto.playlists.UsersPlaylistItemDto

fun List<UsersPlaylistItemDto>.toPlaylistsEntity(): List<PlaylistEntity> {
    return map { apiItem ->
        PlaylistEntity(
            uri = apiItem.uri,
            collaborative = apiItem.collaborative,
            description = apiItem.description,
            name = apiItem.name,
            primaryColor = apiItem.primary_color,
            isPublic = apiItem.public,
            snapshotId = apiItem.snapshot_id,
            totalTrack = apiItem.tracks.total,
            imageUrl = apiItem.images[0].url,
            timestamp = System.currentTimeMillis()

        )
    }
}
