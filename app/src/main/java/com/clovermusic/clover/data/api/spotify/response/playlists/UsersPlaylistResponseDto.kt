package com.clovermusic.clover.data.api.spotify.response.playlists

import com.clovermusic.clover.data.api.spotify.response.common.ImageResponseDto
import com.clovermusic.clover.data.api.spotify.response.common.OwnerResponseDto

data class CurrentUsersPlaylistResponseDto(
    val href: String,
    val items: List<UsersPlaylistItemDto>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any,
    val total: Int
)

data class UsersPlaylistItemDto(
    val collaborative: Boolean,
    val description: String,
    val href: String,
    val id: String,
    val images: List<ImageResponseDto>,
    val name: String,
    val owner: OwnerResponseDto,
    val primary_color: String,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: UsersPlaylistTracksDto,
    val type: String,
    val uri: String
)

data class UsersPlaylistTracksDto(
    val href: String,
    val total: Int
)