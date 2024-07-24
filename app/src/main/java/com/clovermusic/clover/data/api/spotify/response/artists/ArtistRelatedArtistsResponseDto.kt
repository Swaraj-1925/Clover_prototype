package com.clovermusic.clover.data.api.spotify.response.artists

import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto

data class ArtistRelatedArtistsResponseDto(
    val artists: List<TrackArtistResponseDto>
)
