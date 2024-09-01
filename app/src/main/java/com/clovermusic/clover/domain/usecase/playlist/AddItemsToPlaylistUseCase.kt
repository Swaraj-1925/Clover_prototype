package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.dto.playlists.CreatePlaylistRequest
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.Resource
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ManagePlaylistUseCase @Inject constructor(
    private val repository: Repository,
    private val dataAction: NetworkDataAction
) {

    suspend fun addItemsToPlaylist(playlistId: String, uris: List<String>): Resource<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                dataAction.playlistData.addItemsToPlaylist(playlistId, uris)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            val error = customErrorHandling(e)
            Resource.Error(error)
        }
    }

    suspend fun removeItemsFromPlaylist(playlistId: String, tracks: List<String>): Resource<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                dataAction.playlistData.removePlaylistItems(playlistId, tracks)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            val error = customErrorHandling(e)
            Resource.Error(error)
        }
    }
    suspend fun createPlaylist(
        userId: String,
        playlistRequest: CreatePlaylistRequest
    ): Resource<String> {
        return try {
            withContext(Dispatchers.IO) {
                dataAction.authData.ensureValidAccessToken()
                val userId = repository.user.getAndStoreUserDataFromAPi().userId
                dataAction.playlistData.createNewPlaylist(userId,playlistRequest)
            }
            Resource.Success("Playlist created successfully")
        } catch (e: Exception) {
            val error = customErrorHandling(e)
            Resource.Error(error)
        }
    }
}