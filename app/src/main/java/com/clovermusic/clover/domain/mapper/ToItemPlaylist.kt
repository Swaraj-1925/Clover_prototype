package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.util.PlaylistTrackResponse
import com.clovermusic.clover.domain.model.PlaylistItem
import com.clovermusic.clover.domain.model.util.Artists
import com.clovermusic.clover.domain.model.util.Image

fun toPlaylistItems(response: List<PlaylistTrackResponse>): List<PlaylistItem> {
    return response.map { apiItem ->
        PlaylistItem(
            addedById = apiItem.added_by.id,
            addedByUri = apiItem.added_by.uri,
            artists = apiItem.track.artists.map { apiArtist ->
                Artists(
                    id = apiArtist.id,
                    name = apiArtist.name,
                    type = apiArtist.type,
                    uri = apiArtist.uri
                )
            },
            image = apiItem.track.album.images.map { apiImage ->
                Image(
                    height = apiImage.height,
                    url = apiImage.url,
                    width = apiImage.width
                )
            },
            trackDurationMs = apiItem.track.duration_ms,
            trackId = apiItem.track.id,
            trackName = apiItem.track.name,
            trackPopularity = apiItem.track.popularity,
            trackPreviewUrl = apiItem.track.preview_url,
            trackNumber = apiItem.track.track_number,
            trackUri = apiItem.track.uri
        )
    }
}
