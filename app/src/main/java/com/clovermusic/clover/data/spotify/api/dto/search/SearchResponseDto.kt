package com.clovermusic.clover.data.spotify.api.dto.search

data class SearchResponseDto(
    val albums: Albums?,
    val artists: Artists?,
    val playlists: Playlists?,
    val tracks: TracksX?
)