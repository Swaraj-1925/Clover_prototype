package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.common.PlaylistTrackResponseDto
import com.clovermusic.clover.domain.mapper.Util.toTrackArtists
import com.clovermusic.clover.domain.model.PlaylistItems

fun List<PlaylistTrackResponseDto>.toPlaylistItems(): List<PlaylistItems> {
    return map { apiItem ->
        PlaylistItems(
            addedById = apiItem.added_by.id,
            addedByUri = apiItem.added_by.uri,
            artists = apiItem.track.artists.toTrackArtists(),
            durationMs = apiItem.track.duration_ms,
            id = apiItem.track.id,
            name = apiItem.track.name,
            popularity = apiItem.track.popularity,
            previewUrl = apiItem.track.preview_url,
            uri = apiItem.track.uri
        )

    }
}