package com.clovermusic.clover.data.local.mapper

import com.clovermusic.clover.data.local.entity.PlaylistTrackEntity
import com.clovermusic.clover.data.spotify.api.dto.common.PlaylistTrackResponseDto

fun List<PlaylistTrackResponseDto>.toPlaylistTrackEntity(
    playlistId: String,
): List<PlaylistTrackEntity> {
    return map { item ->
        PlaylistTrackEntity(
            uri = item.track.uri,
            id = item.track.id,
            playlistId = playlistId,
            addedByUri = item.added_by.uri,
            addedById = item.added_by.id,
            name = item.track.name,
            imageUrl = item.track.album.images[0].url,
            previewUrl = item.track.preview_url,
            artistName = item.track.artists[0].name,
            artistUri = item.track.artists[0].uri,
            artistId = item.track.artists[0].id,
            durationMs = item.track.duration_ms
        )
    }
}