package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.spotify.api.dto.common.TrackItemsResponseDto
import com.clovermusic.clover.domain.mapper.Util.toTrackArtists
import com.clovermusic.clover.domain.model.Track

fun List<TrackItemsResponseDto>.toTrack(): List<Track> {
    return map { apiItem ->
        Track(
            artists = apiItem.artists.toTrackArtists(),
            durationMs = apiItem.duration_ms,
            id = apiItem.id,
            name = apiItem.name,
            previewUrl = apiItem.preview_url,
            uri = apiItem.uri,
            popularity = apiItem.popularity
        )
    }
}