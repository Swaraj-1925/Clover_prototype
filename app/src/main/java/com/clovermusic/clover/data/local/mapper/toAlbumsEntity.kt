package com.clovermusic.clover.data.local.mapper

import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto


fun List<AlbumResponseDto>.toAlbumsEntity(): List<AlbumEntity> {
    return map { res ->
        AlbumEntity(
            uri = res.uri,
            name = res.name,
            id = res.id,
            artists = res.artists[0].name,
            artistsUri = res.artists[0].uri,
            artistsId = res.artists[0].id,
            imageUrl = res.images[0].url,
            releaseDate = res.release_date_precision,
            timestamp = System.currentTimeMillis()
        )
    }
}