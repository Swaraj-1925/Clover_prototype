package com.clovermusic.clover.data.spotify.api.response.artists

import com.clovermusic.clover.data.spotify.api.response.common.TrackItemsResponseDto

data class ArtistsTopTracksResponseDto(
    val tracks: List<TrackItemsResponseDto>
)
