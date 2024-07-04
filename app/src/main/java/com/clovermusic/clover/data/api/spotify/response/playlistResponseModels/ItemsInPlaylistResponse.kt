package com.clovermusic.clover.data.api.spotify.response.playlistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.ArtistResponse
import com.clovermusic.clover.data.api.spotify.response.util.ImageResponse

data class ItemsInPlaylistResponse(
    val href: String,
    val items: List<PlaylistItemResponse>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any,
    val total: Int
)

data class PlaylistItemResponse(
    val added_at: String,
    val added_by: ItemsInPlaylistAddedBy,
    val is_local: Boolean,
    val primary_color: Any,
    val track: ItemsInPlaylistTrack,
)

data class ItemsInPlaylistAddedBy(
    val id: String,
    val type: String,
    val uri: String
)

data class ItemsInPlaylistTrack(
    val album: ItemsInPlaylistAlbum,
    val artists: List<ArtistResponse>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Int,
    val episode: Boolean,
    val explicit: Boolean,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String,
    val track: Boolean,
    val track_number: Int,
    val type: String,
    val uri: String
)

data class ItemsInPlaylistAlbum(
    val album_type: String,
    val artists: List<ArtistResponse>,
    val href: String,
    val id: String,
    val images: List<ImageResponse>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)

