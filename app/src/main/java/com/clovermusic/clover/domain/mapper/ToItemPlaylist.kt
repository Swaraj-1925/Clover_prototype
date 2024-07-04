package com.clovermusic.clover.domain.mapper

import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.ItemsInPlaylistAddedBy
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.ItemsInPlaylistResponse
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.ItemsInPlaylistTrack
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.PlaylistItemResponse
import com.clovermusic.clover.data.api.spotify.response.util.ArtistResponse
import com.clovermusic.clover.domain.model.AddedBy
import com.clovermusic.clover.domain.model.ItemPlaylist
import com.clovermusic.clover.domain.model.PlaylistItem
import com.clovermusic.clover.domain.model.Track
import com.clovermusic.clover.domain.model.util.Artists

object ToItemPlaylist {
    fun mapItemsInPlaylistResponseToItemPlaylist(response: ItemsInPlaylistResponse): ItemPlaylist {
        return ItemPlaylist(
            items = response.items.map { mapPlaylistItemResponseToPlaylistItem(it) },
            next = response.next ?: "",
            total = response.total
        )
    }

    private fun mapPlaylistItemResponseToPlaylistItem(item: PlaylistItemResponse): PlaylistItem {
        return PlaylistItem(
            added_by = mapItemsInPlaylistAddedByToAddedBy(item.added_by),
            track = mapItemsInPlaylistTrackToTrack(item.track)
        )
    }

    private fun mapItemsInPlaylistAddedByToAddedBy(addedBy: ItemsInPlaylistAddedBy): AddedBy {
        return AddedBy(
            id = addedBy.id,
            type = addedBy.type,
            uri = addedBy.uri
        )
    }

    private fun mapItemsInPlaylistTrackToTrack(track: ItemsInPlaylistTrack): Track {
        return Track(
            artists = track.artists.map { mapArtistResponseToArtists(it) },
            duration_ms = track.duration_ms,
            id = track.id,
            name = track.name,
            popularity = track.popularity,
            preview_url = track.preview_url,
            track_number = track.track_number,
            uri = track.uri
        )
    }

    private fun mapArtistResponseToArtists(artist: ArtistResponse): Artists {
        return Artists(
            id = artist.id,
            name = artist.name,
            type = artist.type,
            uri = artist.uri
        )
    }
}