package com.clovermusic.clover.data.api.spotify.response.artistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.TrackItemsResponse

data class ArtistsTopTracksResponse(
    val tracks: List<TrackItemsResponse>
)