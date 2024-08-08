package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CurrentUsersPlaylistsUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
    suspend operator fun invoke(forceRefresh: Boolean): Flow<DataState<List<PlaylistInfoEntity>>> =
        flow {
            try {
                networkDataAction.authData.ensureValidAccessToken()

                val storedPlaylists = repository.playlists.getStoredCurrentUserPlaylists()


                if (storedPlaylists.isNotEmpty() && !forceRefresh) {
                    emit(DataState.OldData(storedPlaylists))
                } else {
                    val freshPlaylists =
                        repository.playlists.getAndStoreCurrentUserPlaylistsFromApi()
                    val dataUpdated = storedPlaylists != freshPlaylists
                    if (dataUpdated) {
                        emit(DataState.NewData(freshPlaylists))
                    }
                }
            } catch (e: Exception) {
                Log.e(
                    "CurrentUsersPlaylistsUseCase",
                    "Error fetching current users playlists",
                    e
                )
                val storedPlaylists = repository.playlists.getStoredCurrentUserPlaylists()
                if (storedPlaylists.isNotEmpty()) {
                    emit(DataState.OldData(storedPlaylists))
                } else {
                    emit(DataState.Error(customErrorHandling(e)))
                }
            }
        }.flowOn(Dispatchers.IO)
}