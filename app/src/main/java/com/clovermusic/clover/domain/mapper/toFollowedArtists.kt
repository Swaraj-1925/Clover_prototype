package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.ArtistResponseItem
import com.clovermusic.clover.domain.model.FollowedArtists
import com.clovermusic.clover.domain.model.Image

fun toFollowedArtists(response: List<ArtistResponseItem>?): List<FollowedArtists>? {
    return response?.map { apiItem ->
        FollowedArtists(
            followers = apiItem.followers.total,
            genres = apiItem.genres,
            href = apiItem.href,
            id = apiItem.id,
            images = apiItem.images.map { apiImage ->
                Image(
                    height = apiImage.height,
                    url = apiImage.url,
                    width = apiImage.width
                )
            },
            name = apiItem.name,
            popularity = apiItem.popularity,
            type = apiItem.type,
            uri = apiItem.uri
        )
    }
}
