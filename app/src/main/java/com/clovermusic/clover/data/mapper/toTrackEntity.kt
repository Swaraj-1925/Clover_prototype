package com.clovermusic.clover.data.mapper

import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.spotify.api.dto.common.TrackItemsResponseDto

fun List<TrackItemsResponseDto>.toTrackEntity(): List<TrackEntity> {
    return map { apiItem ->
        TrackEntity(
            durationMs = apiItem.duration_ms,
            id = apiItem.id,
            name = apiItem.name,
            previewUrl = apiItem.preview_url,
            uri = apiItem.uri,
            popularity = apiItem.popularity
        )
    }
}
