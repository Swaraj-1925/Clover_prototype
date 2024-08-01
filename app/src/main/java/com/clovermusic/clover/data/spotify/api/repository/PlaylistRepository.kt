package com.clovermusic.clover.data.spotify.api.repository

import android.util.Log
import com.clovermusic.clover.data.spotify.api.dto.common.ImageResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.PlaylistTrackResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.CreatePlaylistRequest
import com.clovermusic.clover.data.spotify.api.dto.playlists.PlaylistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.UsersPlaylistItemDto
import com.clovermusic.clover.data.spotify.api.service.PlaylistService
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
            val userPlaylists =
                mutableListOf<UsersPlaylistItemDto>()
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
                Log.e("PlaylistRepository", "getCurrentUsersPlaylists: $e")
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
    suspend fun getPlaylist(playlistId: String): PlaylistResponseDto =
        withContext(Dispatchers.IO) {
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

    //    Function to add items to playlist
    suspend fun addItemsToPlaylist(playlistId: String, uris: List<String>): Unit =
        withContext(Dispatchers.IO) {
            try {
                playlistService.addItemsToPlaylist(playlistId, uris.toTypedArray())
                Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException(
                    "PlaylistRepository",
                    "addItemsToPlaylist",
                    e
                )
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "PlaylistRepository",
                    "addItemsToPlaylist",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException(
                    "PlaylistRepository",
                    "addItemsToPlaylist",
                    e
                )
            }
        }

    //    Function to remove items from playlist
    suspend fun removePlaylistItems(playlistId: String, tracks: List<String>): Unit =
        withContext(Dispatchers.IO) {
            try {
                playlistService.removePlaylistItems(playlistId, tracks)
                Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException(
                    "PlaylistRepository",
                    "removePlaylistItems",
                    e
                )
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "PlaylistRepository",
                    "removePlaylistItems",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException(
                    "PlaylistRepository",
                    "removePlaylistItems",
                    e
                )
            }

        }

    //    Function to create new playlist
    suspend fun createNewPlaylist(
        userId: String,
        playlistRequest: CreatePlaylistRequest
    ): PlaylistResponseDto =
        withContext(Dispatchers.IO) {
            try {
                val createdPlaylist = playlistService.createPlaylist(userId, playlistRequest)
                Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
                createdPlaylist
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException(
                    "PlaylistRepository",
                    "createNewPlaylist",
                    e
                )
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "PlaylistRepository",
                    "createNewPlaylist",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException(
                    "PlaylistRepository",
                    "createNewPlaylist",
                    e
                )
            }
        }

    //    Function to upload playlist cover image
    suspend fun uploadPlaylistCover(
        playlistId: String,
        image: String
    ): ImageResponseDto =
        withContext(Dispatchers.IO) {
            try {
                val playlist = playlistService.uploadPlaylistCoverImage(playlistId, image)
                Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
                playlist
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException(
                    "PlaylistRepository",
                    "uploadPlaylistCover",
                    e
                )
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "PlaylistRepository",
                    "uploadPlaylistCover",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException(
                    "PlaylistRepository",
                    "uploadPlaylistCover",
                    e
                )
            }
        }

}