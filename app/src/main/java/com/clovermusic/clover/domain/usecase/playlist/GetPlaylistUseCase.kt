package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
    // Get a single playlist and all details about it
    suspend operator fun invoke(
        forceRefresh: Boolean,
        playlistId: String,
    ): Flow<DataState<Playlist>> = flow {
        try {
            networkDataAction.authData.ensureValidAccessToken()
            val getStoredPlaylist = repository.playlists.getStoredPlaylist(playlistId)
            val needRefresh = getStoredPlaylist == null || forceRefresh
            if (needRefresh) {
                val freshPlaylist = repository.playlists.getAndStorePlaylistFromApi(playlistId)
                emit(DataState.NewData(freshPlaylist))
            } else {
                emit(DataState.OldData(getStoredPlaylist))
                val freshPlaylist = repository.playlists.getAndStorePlaylistFromApi(playlistId)

                val dataChanged = freshPlaylist != getStoredPlaylist
                if (dataChanged) {
                    emit(DataState.NewData(freshPlaylist))
                }
            }

        } catch (e: Exception) {
            Log.e("GetPlaylistUseCase", "Error getting playlist", e)
            emit(DataState.Error(customErrorHandling(e)))
        }
    }.flowOn(Dispatchers.Default)
}
