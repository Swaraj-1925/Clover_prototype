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

/**
 * Gets all Artists and map them to FollowedArtists data class for UI and emits a new flow
 */
class FollowedArtistsUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
    suspend operator fun invoke(forceRefresh: Boolean): Flow<DataState<List<ArtistsEntity>>> =
        flow {
            try {
                networkDataAction.authData.ensureValidAccessToken()

                val storedFollowedArtists = repository.user.getStoredFollowedArtists()

                if (storedFollowedArtists.isNotEmpty()) {
                    emit(DataState.OldData(storedFollowedArtists))
                }
                val freshFollowedArtists =
                    repository.user.getAndStoreFollowedArtistsFromApi()
                val dataChanged = freshFollowedArtists != storedFollowedArtists
                val needsRefresh = storedFollowedArtists.isEmpty() || forceRefresh || dataChanged

                if (needsRefresh) {
                    Log.d("FollowedArtistsUseCase", "Fetching fresh followed artists")
                    emit(DataState.NewData(freshFollowedArtists))
                }

            } catch (e: Exception) {
                Log.e("FollowedArtistsUseCase", "Error fetching followed artists", e)
                val error = customErrorHandling(e)
                emit(DataState.Error(error))
            }
        }.flowOn(Dispatchers.IO)
}