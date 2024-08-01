package com.clovermusic.clover.data.mapper

import com.clovermusic.clover.data.local.entity.PlaylistItemsEntity
import com.clovermusic.clover.data.spotify.api.dto.common.PlaylistTrackResponseDto

fun List<PlaylistTrackResponseDto>.toPlaylistItemsEntity(): List<PlaylistItemsEntity> {
    return map { apiItem ->
        PlaylistItemsEntity(
            addedAt = apiItem.added_at,
            addedById = apiItem.added_by.id,
            addedByUri = apiItem.added_by.uri,
            durationMs = apiItem.track.duration_ms,
            id = apiItem.track.id,
            name = apiItem.track.name,
            previewUrl = apiItem.track.preview_url,
            uri = apiItem.track.uri,
            trackNumber = apiItem.track.track_number,
            discNumber = apiItem.track.disc_number
        )

    }
}
