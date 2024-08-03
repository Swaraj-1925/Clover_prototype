package com.clovermusic.clover.data.local.mapper

import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.spotify.api.dto.playlists.PlaylistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.UsersPlaylistItemDto

fun List<UsersPlaylistItemDto>.toPlaylistsEntity(): List<PlaylistInfoEntity> {
    return map { apiItem ->
        PlaylistInfoEntity(
            uri = apiItem.uri,
            id = apiItem.id,
            collaborative = apiItem.collaborative,
            description = apiItem.description,
            name = apiItem.name,
            primaryColor = apiItem.primary_color,
            isPublic = apiItem.public,
            snapshotId = apiItem.snapshot_id,
            totalTrack = apiItem.tracks.total,
            imageUrl = apiItem.images[0].url,
            timestamp = System.currentTimeMillis(),
            owner = apiItem.owner.display_name,
            ownerId = apiItem.owner.id,
            followers = 0,
        )
    }
}

fun PlaylistResponseDto.toPlaylistsEntity(): PlaylistInfoEntity {
    return PlaylistInfoEntity(
        uri = uri,
        id = id,
        collaborative = collaborative,
        description = description,
        name = name,
        primaryColor = "",
        isPublic = public,
        snapshotId = snapshot_id,
        totalTrack = tracks.total,
        imageUrl = images[0].url,
        timestamp = System.currentTimeMillis(),
        owner = owner.display_name,
        ownerId = owner.id,
        followers = followers.total ?: 0,
    )

}
