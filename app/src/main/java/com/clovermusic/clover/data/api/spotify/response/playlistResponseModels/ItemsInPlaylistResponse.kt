package com.clovermusic.clover.data.api.spotify.response.playlistResponseModels

import com.clovermusic.clover.data.api.spotify.response.util.FollowersResponse
import com.clovermusic.clover.data.api.spotify.response.util.ImageResponse

data class ItemsInPlaylistResponse(
    val href: String,
    val items: List<PlaylistItemsResponse>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)

data class PlaylistItemsResponse(
    val added_at: String,
    val added_by: ItemsInPlaylistAddedBy,
    val is_local: Boolean,
    val track: ItemsInPlaylistTrack
)

data class ItemsInPlaylistTrack(
    val album: ItemsInPlaylistAlbum,
    val artists: List<ItemsInPlaylistTrackArtist>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: ItemsInPlaylistExternalIds,
    val external_urls: ItemsInPlaylistExternalUrls,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val is_playable: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String?,
    val restrictions: ItemsInPlaylistRestrictions?,
    val track_number: Int,
    val type: String,
    val uri: String
)

data class ItemsInPlaylistAlbum(
    val album_type: String,
    val artists: List<ItemsInPlaylistAlbumArtist>,
    val available_markets: List<String>,
    val external_urls: ItemsInPlaylistExternalUrls,
    val href: String,
    val id: String,
    val images: List<ImageResponse>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val restrictions: ItemsInPlaylistRestrictions?,
    val total_tracks: Int,
    val type: String,
    val uri: String
)

data class ItemsInPlaylistAddedBy(
    val external_urls: ItemsInPlaylistExternalUrls,
    val followers: FollowersResponse?,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)

data class ItemsInPlaylistTrackArtist(
    val external_urls: ItemsInPlaylistExternalUrls,
    val followers: FollowersResponse?,
    val genres: List<String>?,
    val href: String,
    val id: String,
    val images: List<ImageResponse>?,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)

data class ItemsInPlaylistAlbumArtist(
    val external_urls: ItemsInPlaylistExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

data class ItemsInPlaylistExternalIds(
    val ean: String?,
    val isrc: String?,
    val upc: String?
)

data class ItemsInPlaylistExternalUrls(
    val spotify: String
)

data class ItemsInPlaylistRestrictions(
    val reason: String
)

data class ImageResponse(
    val height: Int,
    val url: String,
    val width: Int
)

data class FollowersResponse(
    val href: String?,
    val total: Int
)
