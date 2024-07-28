package com.clovermusic.clover.data.api.spotify.response.artists

import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto

// Used in Artist Repository in getArtistRelatedArtists function to fetch related artist

data class ArtistRelatedArtistsResponseDto(
    val artists: List<TrackArtistResponseDto>
)
