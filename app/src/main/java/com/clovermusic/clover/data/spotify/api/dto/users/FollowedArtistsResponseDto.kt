package com.clovermusic.clover.data.spotify.api.dto.users

import com.clovermusic.clover.data.spotify.api.dto.common.CursorsResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto

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
