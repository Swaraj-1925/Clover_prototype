package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.common.AlbumResponseDto
import com.clovermusic.clover.domain.mapper.Util.toAlbumArtist
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.model.Albums
// Objects of AlbumResponseDto are mapped in ArtistAlbums() and creates List of Album objects
fun List<AlbumResponseDto>.toArtistAlbums(): List<Albums> {
    return map { apiItem ->
        Albums(
            artists = apiItem.artists.toAlbumArtist(),
            image = apiItem.images.toImages(),
            totalTracks = apiItem.total_tracks,
            albumId = apiItem.id,
            albumName = apiItem.name,
            releaseDate = apiItem.release_date,
            uri = apiItem.uri
        )
    }
}