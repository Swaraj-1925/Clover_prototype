package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TopArtistUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
    suspend operator fun invoke(
        timeRange: String = "short_term",
        forceRefresh: Boolean = false
    ): Flow<DataState<List<ArtistsEntity>>> = flow {
        try {
            networkDataAction.authData.ensureValidAccessToken()
            val storedFollowedArtists = repository.user.getStoredTopArtists()
            val needsRefresh = storedFollowedArtists.isEmpty() || forceRefresh
            if (needsRefresh) {
                val freshTopArtists = repository.user.getAndStoreTopArtistsFromApi(timeRange)
                emit(DataState.NewData(freshTopArtists))
            } else {
                emit(DataState.OldData(storedFollowedArtists))
                val freshTopArtists = repository.user.getAndStoreTopArtistsFromApi(timeRange)
                val isDataChanged = freshTopArtists != storedFollowedArtists

                if (isDataChanged) {
                    emit(DataState.NewData(freshTopArtists))
                }
            }
        } catch (e: Exception) {
            Log.e("FollowedArtistsUseCase", "Error fetching followed artists", e)

            val storedFollowedArtists = repository.user.getStoredTopArtists()
            if (storedFollowedArtists.isNotEmpty()) {
                emit(DataState.OldData(storedFollowedArtists))
            } else {
                emit(DataState.Error(customErrorHandling(e)))
            }
        }
    }.flowOn(Dispatchers.IO)
}