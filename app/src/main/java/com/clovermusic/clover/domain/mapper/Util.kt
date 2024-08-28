package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.spotify.api.dto.common.AlbumArtistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.ImageResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.OwnerResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto
import com.clovermusic.clover.domain.model.Albums
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

    fun ImageResponseDto.toImages(): Image {
        return Image(
            height = height,
            url = url,
            width = width
        )
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
                followers = apiArtist.followers?.total ?: 0,
                genres = apiArtist.genres,
                id = apiArtist.id,
                images = apiArtist.images?.toImages() ?: emptyList(),
                name = apiArtist.name ?: " ",
                popularity = apiArtist.popularity ?: 0,
                uri = apiArtist.uri
            )
        }
    }

    fun AlbumResponseDto.toAlbum(): Albums {
        return Albums(
            artists = artists.toAlbumArtist(),
            image = images[0].toImages(),
            totalTracks = total_tracks,
            albumId = id,
            albumName = name,
            releaseDate = release_date,
            uri = uri
        )
    }

}