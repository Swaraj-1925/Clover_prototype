package com.clovermusic.clover.data.spotify.api.dto.albums

import com.clovermusic.clover.data.spotify.api.dto.common.AlbumArtistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.ImageResponseDto

data class SpecificAlbumResponseDto(
    val album_type: String,
    val artists: List<AlbumArtistResponseDto>,
    val available_markets: List<String>,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<ImageResponseDto>,
    val label: String,
    val name: String,
    val popularity: Int,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val tracks: AlbumTracksDto,
    val type: String,
    val uri: String
)