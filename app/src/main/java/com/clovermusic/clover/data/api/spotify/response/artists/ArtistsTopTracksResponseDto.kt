package com.clovermusic.clover.data.api.spotify.response.artists

import com.clovermusic.clover.data.api.spotify.response.common.TrackItemsResponseDto
/* Used in Artist Service.kt in getArtistTopTracks function to get top artist.
    getArtistTopTracks is used in Artist Repository */
data class ArtistsTopTracksResponseDto(
    val tracks: List<TrackItemsResponseDto>
)
