package com.clovermusic.clover.data.spotify.models.playlist.playlistTracks

data class PlaylistTracksResponseItem(
    val added_at: String,
    val added_by: AddedBy,
    val is_local: Boolean,
    val track: Track,
    val video_thumbnail: VideoThumbnail
)

data class Track(
    val album: Album,
    val artists: List<ArtistX>,
    val disc_number: Int,
    val duration_ms: Int,
    val episode: Boolean,
    val explicit: Boolean,
    val href: String,
    val id: String,
    val name: String,
    val popularity: Int,
    val preview_url: String,
    val track: Boolean,
    val track_number: Int,
    val type: String,
    val uri: String
)

data class AddedBy(
    val id: String,
    val type: String,
    val uri: String
)

data class Album(
    val album_type: String,
    val artists: List<ArtistX>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)

data class ArtistX(
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int
)

data class VideoThumbnail(
    val url: Any
)
