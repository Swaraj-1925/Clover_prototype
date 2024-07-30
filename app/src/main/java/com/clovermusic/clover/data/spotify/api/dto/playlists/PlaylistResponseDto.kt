package com.clovermusic.clover.data.spotify.api.dto.playlists

import com.clovermusic.clover.data.spotify.api.dto.common.FollowersResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.ImageResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.OwnerResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.PlaylistTrackResponseDto

data class PlaylistResponseDto(
    val collaborative: Boolean,
    val description: String,
    val followers: FollowersResponseDto,
    val href: String,
    val id: String,
    val images: List<ImageResponseDto>,
    val name: String,
    val owner: OwnerResponseDto,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: TracksDto,
    val type: String,
    val uri: String
)

data class TracksDto(
    val href: String,
    val items: List<PlaylistTrackResponseDto>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)