package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.ArtistAlbumsResponseItem
import com.clovermusic.clover.domain.model.AlbumsArtist
import com.clovermusic.clover.domain.model.ArtistAlbums
import com.clovermusic.clover.domain.model.Image


fun toArtistAlbums(response: List<ArtistAlbumsResponseItem>?): List<ArtistAlbums>? {
    return response?.map { apiItem ->
        ArtistAlbums(
            album_group = apiItem.album_group,
            album_type = apiItem.album_type,
            id = apiItem.id,
            artists = apiItem.artists.map { apiArtist ->
                AlbumsArtist(
                    id = apiArtist.id,
                    name = apiArtist.name,
                    type = apiArtist.type,
                    uri = apiArtist.uri
                )
            },
            images = apiItem.images.map { apiImage ->
                Image(
                    height = apiImage.height,
                    url = apiImage.url,
                    width = apiImage.width
                )
            },
            name = apiItem.name,
            release_date = apiItem.release_date,
            total_tracks = apiItem.total_tracks,
            type = apiItem.type,
            uri = apiItem.uri
        )
    }
}