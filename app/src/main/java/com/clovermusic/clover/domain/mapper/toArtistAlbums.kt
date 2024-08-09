package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto
import com.clovermusic.clover.domain.mapper.Util.toAlbumArtist
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.model.Albums

fun List<AlbumResponseDto>.toArtistAlbums(): List<Albums> {
    return map { apiItem ->
        Albums(
            artists = apiItem.artists.toAlbumArtist(),
            image = apiItem.images[0]!!.toImages(),
            totalTracks = apiItem.total_tracks,
            albumId = apiItem.id,
            albumName = apiItem.name,
            releaseDate = apiItem.release_date,
            uri = apiItem.uri
        )
    }
}