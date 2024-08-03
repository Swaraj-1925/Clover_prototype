package com.clovermusic.clover.data.local.mapper

import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto

fun List<TrackArtistResponseDto>.toArtistEntity(
    isFollowed: Boolean = false,
    isTopArtist: Boolean = false
): List<ArtistsEntity> {
    return mapNotNull { res ->
        val imageUrl = res.images?.firstOrNull()?.url ?: ""
        ArtistsEntity(
            uri = res.uri,
            imageUrl = imageUrl,
            name = res.name,
            isFollowed = isFollowed,
            isTopArtist = isTopArtist,
            artistsId = res.id,
            timestamp = System.currentTimeMillis()
        )
    }
}
