package com.clovermusic.clover.data.api.spotify.response.users

import com.clovermusic.clover.data.api.spotify.response.common.CursorsResponseDto
import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto

data class FollowedArtistsResponseDto(
    val cursors: CursorsResponseDto,
    val href: String,
    val items: List<TrackArtistResponseDto>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val total: Int
)
