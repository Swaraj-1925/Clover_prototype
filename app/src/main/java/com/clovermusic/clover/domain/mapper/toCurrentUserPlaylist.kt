package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.CurrentUsersPlaylistItem
import com.clovermusic.clover.domain.model.CurrentUserPlaylist
import com.clovermusic.clover.domain.model.Owner
import com.clovermusic.clover.domain.model.util.Image

fun toCurrentUserPlaylist(response: List<CurrentUsersPlaylistItem>?): List<CurrentUserPlaylist>? {
    return response?.map { apiItem ->
        CurrentUserPlaylist(
            collaborative = apiItem.collaborative,
            description = apiItem.description,
            id = apiItem.id,
            images = apiItem.images.map { apiImage ->
                Image(
                    height = apiImage.height,
                    url = apiImage.url,
                    width = apiImage.width
                )
            },
            name = apiItem.name,
            owner = Owner(
                display_name = apiItem.owner.display_name,
                id = apiItem.owner.id,
                type = apiItem.owner.type,
                uri = apiItem.owner.uri
            ),
            primary_color = apiItem.primary_color,
            public = apiItem.public,
            snapshot_id = apiItem.snapshot_id,
            tracks = apiItem.tracks.total,
            type = apiItem.type,
            uri = apiItem.uri
        )
    }
}
