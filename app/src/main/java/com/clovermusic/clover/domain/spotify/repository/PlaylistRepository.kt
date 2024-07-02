package com.clovermusic.clover.domain.spotify.repository

import com.clovermusic.clover.data.spotify.models.playlist.currentUserPlaylists.CurrentUserPlaylistResponse
import com.clovermusic.clover.data.spotify.models.playlist.playlistTracks.PlaylistTracksResponse

interface PlaylistRepository {

    suspend fun getCurrentPlaylist(
        offset: Int,
        limit: Int
    ): CurrentUserPlaylistResponse

    suspend fun getPlaylistTracks(
        playlistId: String,
        offset: Int,
        limit: Int
    ): PlaylistTracksResponse
}