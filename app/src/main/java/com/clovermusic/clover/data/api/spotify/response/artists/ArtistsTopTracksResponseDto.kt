package com.clovermusic.clover.data.api.spotify.response.artists

import com.clovermusic.clover.data.api.spotify.response.common.TrackItemsResponseDto

data class ArtistsTopTracksResponseDto(
    val tracks: List<TrackItemsResponseDto>
)
