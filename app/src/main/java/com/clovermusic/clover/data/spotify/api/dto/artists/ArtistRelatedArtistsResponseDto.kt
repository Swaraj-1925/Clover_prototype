package com.clovermusic.clover.data.spotify.api.dto.artists

import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto

data class ArtistRelatedArtistsResponseDto(
    val artists: List<TrackArtistResponseDto>
)
