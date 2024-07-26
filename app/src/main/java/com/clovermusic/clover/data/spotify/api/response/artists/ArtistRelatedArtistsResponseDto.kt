package com.clovermusic.clover.data.spotify.api.response.artists

import com.clovermusic.clover.data.spotify.api.response.common.TrackArtistResponseDto

data class ArtistRelatedArtistsResponseDto(
    val artists: List<TrackArtistResponseDto>
)
