package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.CurrentUsersPlaylistItem
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.PlaylistItemResponse
import com.clovermusic.clover.data.api.spotify.service.PlaylistService
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val playlistService: PlaylistService
) {
    suspend fun getCurrentUsersPlaylists(): Flow<Resource<List<CurrentUsersPlaylistItem>>> =
        flow {
            emit(Resource.Loading())

            val userPlaylists = mutableListOf<CurrentUsersPlaylistItem>()
            try {
                var response = playlistService.getCurrentUsersPlaylists()
                userPlaylists.addAll(response.items)
                while (response.next != null) {
                    response = playlistService.getNextPage(response.next!!)
                    userPlaylists.addAll(response.items)
                }
                Log.i("PlaylistRepository", "getCurrentUsersPlaylists : Success")
                emit(Resource.Success(userPlaylists))

            } catch (e: Exception) {
                Log.e("PlaylistRepository", "getCurrentUsersPlaylists  Exception: ", e)
                emit(Resource.Error("Unknown error. Please contact support for assistance."))
            } catch (e: IOException) {
                Log.e("PlaylistRepository", "getCurrentUsersPlaylists IOException: ${e.message}", e)
                emit(Resource.Error("Network error occurred during authentication. Please try again later."))
            }
        }

    suspend fun getPlaylistItems(playlistId: String): Flow<Resource<List<PlaylistItemResponse>>> =
        flow {
            emit(Resource.Loading())

            val playlistsItems = mutableListOf<PlaylistItemResponse>()
            try {
                var response = playlistService.getPlaylistItems(playlistId)
                playlistsItems.addAll(response.items)
                while (response.next != null) {
                    response = playlistService.getNextPage(response.next!!)
                    playlistsItems.addAll(response.items)
                }
                Log.i("PlaylistRepository", "getPlaylistItems : Success")
                emit(Resource.Success(playlistsItems))

            } catch (e: Exception) {
                Log.e("PlaylistRepository", "getPlaylistItems Exception: ", e)
                emit(Resource.Error("Unknown error. Please contact support for assistance."))
            } catch (e: IOException) {
                Log.e("PlaylistRepository", "getPlaylistItems IOException: ${e.message}", e)
                emit(Resource.Error("Network error occurred during authentication. Please try again later."))
            }
        }
}