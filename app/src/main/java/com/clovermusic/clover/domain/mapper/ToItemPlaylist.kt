package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.PlaylistItemResponse
import com.clovermusic.clover.domain.model.PlaylistItem
import com.clovermusic.clover.domain.model.util.Artists

fun toItemPlaylist(response: List<PlaylistItemResponse>): List<PlaylistItem> {
    return response.map { apiItem ->
        PlaylistItem(
            addedById = apiItem.added_by.id,
            addedByUri = apiItem.added_by.uri,
            artists = apiItem.track.artists.map { apiArtist ->
                Artists(
                    id = apiArtist.id,
                    name = apiArtist.name,
                    type = apiArtist.type,
                    uri = apiArtist.uri,
                )
            },
            trackDurationMs = apiItem.track.duration_ms,
            trackId = apiItem.track.id,
            trackName = apiItem.track.name,
            trackPopularity = apiItem.track.popularity,
            trackPreviewUrl = apiItem.track.preview_url,
            trackNumber = apiItem.track.track_number,
            trackUri = apiItem.track.uri,
        )
    }
}