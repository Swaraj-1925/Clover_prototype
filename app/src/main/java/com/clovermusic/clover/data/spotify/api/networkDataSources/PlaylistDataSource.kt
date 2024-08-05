package com.clovermusic.clover.data.spotify.api.networkDataSources


import android.util.Log
import com.clovermusic.clover.data.spotify.api.dto.common.PlaylistTrackResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.PlaylistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.UsersPlaylistItemDto
import com.clovermusic.clover.data.spotify.api.service.PlaylistService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistDataSource @Inject constructor(
    private val playlistService: PlaylistService
) {

    suspend fun fetchCurrentUsersPlaylists(): List<UsersPlaylistItemDto> =
        withContext(Dispatchers.IO) {
            val userPlaylists =
                mutableListOf<UsersPlaylistItemDto>()
            var offset = 0
            val limit = 50
            var total: Int

            do {
                val response = playlistService.getCurrentUsersPlaylists(offset, limit)
                userPlaylists.addAll(response.items)
                total = response.total
                offset += limit
                Log.d(
                    "PlaylistRepository",
                    "getCurrentUsersPlaylists: fetched batch, size: ${response.total} and offset: $offset"
                )
            } while (offset < total)

            Log.i(
                "PlaylistRepository",
                "getCurrentUsersPlaylists: Success, total playlists: ${userPlaylists.size}"
            )
            userPlaylists
        }

    suspend fun fetchPlaylist(playlistId: String): PlaylistResponseDto =
        withContext(Dispatchers.IO) {
            val playlist: PlaylistResponseDto
            val playlistItems = mutableListOf<PlaylistTrackResponseDto>()
            var nextUrl: String? = null
            val res = playlistService.getPlaylist(playlistId = playlistId)
            playlist = res
            nextUrl = res.tracks.next
            playlistItems.addAll(res.tracks.items)  // Add the first batch of items

            while (nextUrl != null) {
                val response = playlistService.getPlaylistTracks(nextUrl)
                playlistItems.addAll(response.items)
                nextUrl = response.next
                Log.d(
                    "PlaylistRepository",
                    "Fetched batch: ${response.items.size}, Total fetched: ${playlistItems.size}"
                )
            }

            playlist.tracks.items = playlistItems

            Log.d(
                "PlaylistRepository",
                "getPlaylist: total items fetched: ${playlistItems.size}"
            )
            playlist

        }

    suspend fun fetchPlaylistItems(playlistId: String): List<PlaylistTrackResponseDto> =
        withContext(Dispatchers.IO) {
            val playlistItems =
                mutableListOf<PlaylistTrackResponseDto>()
            var offset = 0
            var total: Int

            do {
                val response = playlistService.getPlaylistItems(playlistId, offset)
                playlistItems.addAll(response.items)
                total = response.total
                offset += response.limit
                Log.d(
                    "PlaylistRepository",
                    "fetchPlaylistItems: fetched batch, size: ${response.items} and offset: $offset"
                )
            } while (offset < total)

            Log.d(
                "PlaylistRepository",
                "fetchPlaylistItems: total items fetched: ${playlistItems.size}"
            )
            playlistItems
        }
}