package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.common.PlaylistTrackResponseDto
import com.clovermusic.clover.data.api.spotify.response.playlists.PlaylistResponseDto
import com.clovermusic.clover.data.api.spotify.response.playlists.UsersPlaylistItemDto
import com.clovermusic.clover.data.api.spotify.service.PlaylistService
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.SpotifyApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// Repository for Playlist related api call
class PlaylistRepository @Inject constructor(
    private val playlistService: PlaylistService
) {

    //    Function to return current(Logged in user) users playlists
    suspend fun getCurrentUsersPlaylists(): List<UsersPlaylistItemDto> =
        withContext(Dispatchers.IO) {
            val userPlaylists = mutableListOf<UsersPlaylistItemDto>()
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
                userPlaylists
            } catch (e: IOException) {
                throw CustomException.NetworkException(
                    "PlaylistRepository",
                    "getCurrentUsersPlaylists",
                    e
                )
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "PlaylistRepository",
                    "getCurrentUsersPlaylists",
                    "Unknown error",
                    e
                )
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException(
                    "PlaylistRepository",
                    "getCurrentUsersPlaylists",
                    e
                )
            }
        }

    //    Function to return playlist items
//    TODO: Remove if never used
    suspend fun getPlaylistItems(playlistId: String): List<PlaylistTrackResponseDto> =
        withContext(Dispatchers.IO) {
            try {
                val playlistItems = mutableListOf<PlaylistTrackResponseDto>()
                var offset = 0
                var total: Int

                do {
                    val response = playlistService.getPlaylistItems(playlistId, offset)
                    playlistItems.addAll(response.items)
                    total = response.total
                    offset += response.limit
                    Log.d(
                        "PlaylistRepository",
                        "getPlaylistItems: fetched batch, size: ${response.items} and offset: $offset"
                    )
                } while (offset < total)

                Log.d(
                    "PlaylistRepository",
                    "getPlaylistItems: total items fetched: ${playlistItems.size}"
                )
                playlistItems
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "PlaylistRepository",
                    "getPlaylistItems",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException("PlaylistRepository", "getPlaylistItems", e)
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException("PlaylistRepository", "getPlaylistItems", e)
            }
        }

    // Function to get enter playlist and all the data related to it
    suspend fun getPlaylist(playlistId: String): PlaylistResponseDto = withContext(Dispatchers.IO) {
        try {
            var playlist: PlaylistResponseDto
            var offset = 0
            var total: Int
            do {
                playlist = playlistService.getPlaylist(playlistId)
                total = playlist.tracks.total
                offset += playlist.tracks.limit
                Log.d(
                    "PlaylistRepository",
                    "getPlaylist: fetched batch, size: ${playlist.tracks.total} and offset: $offset"
                )
            } while (offset < total)

            Log.d(
                "PlaylistRepository",
                "getPlaylist: total items fetched: ${playlist.tracks.items.size}"
            )
            playlist
        } catch (e: Exception) {
            throw CustomException.UnknownException(
                "PlaylistRepository",
                "getPlaylist",
                "Unknown error",
                e
            )
        } catch (e: IOException) {
            throw CustomException.NetworkException("PlaylistRepository", "getPlaylist", e)
        } catch (e: HttpException) {
            SpotifyApiException.handleApiException("PlaylistRepository", "getPlaylist", e)
        }
    }

}