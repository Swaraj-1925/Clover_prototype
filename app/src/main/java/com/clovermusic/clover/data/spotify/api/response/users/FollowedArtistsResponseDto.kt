package com.clovermusic.clover.data.spotify.api.response.users

import com.clovermusic.clover.data.spotify.api.response.common.CursorsResponseDto
import com.clovermusic.clover.data.spotify.api.response.common.TrackArtistResponseDto

data class FollowedArtistsDto(
    val artists: FollowedArtistsResponseDto
)

data class FollowedArtistsResponseDto(
    val cursors: CursorsResponseDto?,
    val href: String,
    val items: List<TrackArtistResponseDto>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val total: Int
)
