package com.clovermusic.clover.data.spotify.repository

import com.clovermusic.clover.data.spotify.models.playlist.currentUserPlaylists.CurrentUserPlaylistResponse
import com.clovermusic.clover.data.spotify.models.playlist.playlistTracks.PlaylistTracksResponse
import com.clovermusic.clover.data.spotify.network.PlaylistApiServices
import com.clovermusic.clover.domain.spotify.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val apiService: PlaylistApiServices
) : PlaylistRepository {

    override suspend fun getCurrentPlaylist(offset: Int, limit: Int): CurrentUserPlaylistResponse {
        return apiService.getCurrentUserPlaylists(offset, limit)
    }

    override suspend fun getPlaylistTracks(
        playlistId: String,
        offset: Int,
        limit: Int
    ): PlaylistTracksResponse {
        return apiService.getPlaylistTracks(playlistId, offset, limit)
    }

}