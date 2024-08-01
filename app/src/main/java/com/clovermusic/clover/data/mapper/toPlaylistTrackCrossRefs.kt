package com.clovermusic.clover.data.mapper

import com.clovermusic.clover.data.local.crossRef.PlaylistTrackCrossRef
import com.clovermusic.clover.data.spotify.api.dto.playlists.PlaylistResponseDto

fun PlaylistResponseDto.toPlaylistTrackCrossRefs() = tracks.items.map { playlistTrack ->
    PlaylistTrackCrossRef(
        playlistId = id,
        trackId = playlistTrack.track.id,
        addedAt = playlistTrack.added_at,
        addedById = playlistTrack.added_by.id,
        addedByUri = playlistTrack.added_by.uri
    )
}
