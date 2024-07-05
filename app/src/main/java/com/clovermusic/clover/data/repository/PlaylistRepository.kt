package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.CurrentUsersPlaylistItem
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.ItemsInPlaylistResponse
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.PlaylistItemsResponse
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
                var nextPage = response.next

                while (nextPage != null) {
                    response = playlistService.getNextPage(nextPage)
                    userPlaylists.addAll(response.items)
                    nextPage = response.next
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


    suspend fun getPlaylistItems(playlistId: String): Flow<Resource<List<PlaylistItemsResponse>>> =
        flow {
            emit(Resource.Loading())

            val playlistsItems = mutableListOf<PlaylistItemsResponse>()
            var offset = 0
            var total = -1
            var response: ItemsInPlaylistResponse

            try {
                do {
                    response = playlistService.getPlaylistItems(playlistId, offset)
                    playlistsItems.addAll(response.items)
                    total = response.total
                    offset += response.limit
                } while (offset < total)

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