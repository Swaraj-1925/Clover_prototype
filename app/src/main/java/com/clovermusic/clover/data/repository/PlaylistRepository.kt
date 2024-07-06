package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.CurrentUsersPlaylistItem
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.PlaylistItemsResponse
import com.clovermusic.clover.data.api.spotify.service.PlaylistService
import java.io.IOException
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val playlistService: PlaylistService
) {
    suspend fun getCurrentUsersPlaylists(): List<CurrentUsersPlaylistItem> {
        val userPlaylists = mutableListOf<CurrentUsersPlaylistItem>()
        var offset = 0
        val limit = 50
        var total: Int

        try {
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
            return userPlaylists
        } catch (e: IOException) {
            Log.e("PlaylistRepository", "Network error while fetching playlists", e)
            throw PlaylistFetchException("Network error occurred while fetching playlists", e)
        } catch (e: Exception) {
            Log.e("PlaylistRepository", "Unexpected error while fetching playlists", e)
            throw PlaylistFetchException("An unexpected error occurred while fetching playlists", e)
        }
    }

    class PlaylistFetchException(message: String, cause: Throwable? = null) :
        Exception(message, cause)

    suspend fun getPlaylistItems(playlistId: String): List<PlaylistItemsResponse> {
        return try {
            val playlistItems = mutableListOf<PlaylistItemsResponse>()
            var offset = 0
            var total: Int

            do {
                val response = playlistService.getPlaylistItems(playlistId, offset)
                playlistItems.addAll(response.items)
                total = response.total
                offset += response.limit
                Log.d(
                    "PlaylistRepository",
                    "getPlaylistItems: fetched batch, size: ${response.total} and offset: $offset"
                )
            } while (offset < total)

            Log.d(
                "PlaylistRepository",
                "getPlaylistItems: total items fetched: ${playlistItems.size}"
            )
            playlistItems
        } catch (e: Exception) {
            Log.e("PlaylistRepository", "Error fetching playlist items: ${e.message}", e)
            throw e
        }
    }

}