package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.common.AlbumArtistResponseDto
import com.clovermusic.clover.data.api.spotify.response.common.ImageResponseDto
import com.clovermusic.clover.data.api.spotify.response.common.OwnerResponseDto
import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto
import com.clovermusic.clover.domain.model.common.AlbumArtist
import com.clovermusic.clover.domain.model.common.Image
import com.clovermusic.clover.domain.model.common.Owner
import com.clovermusic.clover.domain.model.common.TrackArtists

object Util {
    fun List<AlbumArtistResponseDto>.toAlbumArtist(): List<AlbumArtist> {
        return map { apiArtist ->
            AlbumArtist(
                id = apiArtist.id,
                name = apiArtist.name,
                type = apiArtist.type,
                uri = apiArtist.uri
            )
        }
    }

    fun List<ImageResponseDto>.toImages(): List<Image> {
        return map { apiImage ->
            Image(
                height = apiImage.height,
                url = apiImage.url,
                width = apiImage.width
            )
        }
    }

    fun OwnerResponseDto.toOwner(): Owner {
        return Owner(
            display_name = display_name,
            id = id,
            type = type,
            uri = uri
        )
    }

    fun List<TrackArtistResponseDto>.toTrackArtists(): List<TrackArtists> {
        return map { apiArtist ->
            TrackArtists(
                followers = apiArtist.followers.total,
                genres = apiArtist.genres,
                id = apiArtist.id,
                images = apiArtist.images.toImages(),
                name = apiArtist.name,
                popularity = apiArtist.popularity,
                uri = apiArtist.uri
            )
        }
    }

}