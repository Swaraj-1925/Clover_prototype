package com.clovermusic.clover.data.spotify.api.dto.artists

import com.clovermusic.clover.data.spotify.api.dto.common.TrackItemsResponseDto

data class ArtistsTopTracksResponseDto(
    val tracks: List<TrackItemsResponseDto>
)
