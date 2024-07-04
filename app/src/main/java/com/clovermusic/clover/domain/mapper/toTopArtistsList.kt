package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.userResponseModels.TopArtistsItem
import com.clovermusic.clover.domain.model.TopArtists
import com.clovermusic.clover.domain.model.util.Image

fun toTopArtists(response: List<TopArtistsItem>?): List<TopArtists> {
    if (response == null) {
        return emptyList()
    }
    return response.map { apiItem ->
        TopArtists(
            followers = apiItem.followers.total,
            genres = apiItem.genres,
            id = apiItem.id,
            image = apiItem.images.map { apiImage ->
                Image(
                    height = apiImage.height,
                    url = apiImage.url,
                    width = apiImage.width
                )
            },
            name = apiItem.name,
            popularity = apiItem.popularity,
            type = apiItem.type,
            uri = apiItem.uri,
        )
    }
}