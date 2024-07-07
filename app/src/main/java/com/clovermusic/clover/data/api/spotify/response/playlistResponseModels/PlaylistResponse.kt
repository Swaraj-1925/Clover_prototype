package com.clovermusic.clover.data.api.spotify.response.playlistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.FollowersResponse
import com.clovermusic.clover.data.api.spotify.response.util.ImageResponse
import com.clovermusic.clover.data.api.spotify.response.util.OwnerResponse
import com.clovermusic.clover.data.api.spotify.response.util.PlaylistTrackResponse

data class PlaylistResponse(
    val collaborative: Boolean,
    val description: String,
    val followers: FollowersResponse,
    val href: String,
    val id: String,
    val images: List<ImageResponse>,
    val name: String,
    val owner: OwnerResponse,
    val public: Boolean,
    val snapshot_id: String,
    val tracks: Tracks,
    val type: String,
    val uri: String
)

data class Tracks(
    val href: String,
    val items: List<PlaylistTrackResponse>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)



