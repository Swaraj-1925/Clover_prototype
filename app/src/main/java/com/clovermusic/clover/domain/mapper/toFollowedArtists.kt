package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.model.common.TrackArtists
// Object of TrackArtistResponseDto are mapped in toFollowedArtists() and creates the list.
fun List<TrackArtistResponseDto>.toFollowedArtists(): List<TrackArtists> {
    return map { apiItem ->
        TrackArtists(
            followers = apiItem.followers?.total ?: 0,
            genres = apiItem.genres,
            id = apiItem.id,
            images = apiItem.images?.toImages() ?: emptyList(),
            name = apiItem.name,
            popularity = apiItem.popularity,
            uri = apiItem.uri
        )
    }
}