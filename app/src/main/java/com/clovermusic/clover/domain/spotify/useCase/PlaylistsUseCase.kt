package com.clovermusic.clover.domain.spotify.useCase

import android.util.Log
import com.clovermusic.clover.data.spotify.models.playlist.currentUserPlaylists.CurrentUserPlaylistResponseItem
import com.clovermusic.clover.data.spotify.models.playlist.playlistTracks.PlaylistTracksResponseItem
import com.clovermusic.clover.domain.spotify.models.CurrentUserPlaylists
import com.clovermusic.clover.domain.spotify.models.PlaylistTracks
import com.clovermusic.clover.domain.spotify.repository.PlaylistRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class PlaylistsUseCase @Inject constructor(
    private val playlistRepository: PlaylistRepository
) {

    //    Function to return Current User , User who is logged in playlist
    suspend fun getUserPlaylists(): List<CurrentUserPlaylists> = coroutineScope {
        val currentUserPlaylists = mutableListOf<CurrentUserPlaylistResponseItem>()
        var offset = 0
        val limit = 50
        var total: Int
        try {
            do {
                val response = playlistRepository.getCurrentPlaylist(0, 50)
                currentUserPlaylists.addAll(response.items)
                offset += limit
                total = response.total
            } while (offset < total)

            val playlists = currentUserPlaylists.map { playlist ->
                CurrentUserPlaylists(
                    collaborative = playlist.collaborative,
                    description = playlist.description,
                    id = playlist.id,
                    images = playlist.images.firstOrNull()?.url ?: "",
                    name = playlist.name,
                    owner = playlist.owner,
                    public = playlist.public,
                    tracksUrl = playlist.tracks.href
                )
            }

            return@coroutineScope playlists
        } catch (e: Exception) {
            Log.e("PlaylistsUseCase", "Failed to get user playlists:- ", e)
            return@coroutineScope emptyList()
        }
    }

    //    Function to return Songs in the playlist
    suspend fun getPlaylistTracks(playlistId: String): List<PlaylistTracks> = coroutineScope {
        val playlistTrack = mutableListOf<PlaylistTracksResponseItem>()
        var offset = 0
        val limit = 50
        var total: Int

        try {
            do {
                val response = playlistRepository.getPlaylistTracks(playlistId, offset, limit)
                playlistTrack.addAll(response.items)
                offset += limit
                total = response.total
            } while (offset < total)

            val tracks = playlistTrack.map { track ->
                PlaylistTracks(
                    artistId = track.track.artists.firstOrNull()?.id ?: "",
                    artistName = track.track.artists.map { it.name },
                    imageUrl = track.track.album.images.firstOrNull()?.url ?: "",
                    trackId = track.track.id,
                    trackUri = track.track.uri,
                    trackName = track.track.name
                )
            }
            return@coroutineScope tracks

        } catch (e: Exception) {
            Log.e("PlaylistsUseCase", "Failed to get user playlists Track:- ", e)
            return@coroutineScope emptyList()
        }
    }
}