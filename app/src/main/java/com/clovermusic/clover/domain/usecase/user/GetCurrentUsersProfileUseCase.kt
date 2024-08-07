package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentUsersProfileUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
    suspend operator fun invoke(forceRefresh: Boolean): Flow<DataState<UserEntity>> = flow {
        try {
            networkDataAction.authData.ensureValidAccessToken()
            val storedUserProfile = repository.user.getStoredUserData()
            val needsRefresh = storedUserProfile == null || forceRefresh
            if (needsRefresh) {
                val freshUserProfile = repository.user.getAndStoreUserDataFromAPi()
                emit(DataState.NewData(freshUserProfile))
            } else {
                emit(DataState.OldData(storedUserProfile))
                val freshUserProfile = repository.user.getAndStoreUserDataFromAPi()

                val isDataChanged = freshUserProfile != storedUserProfile
                if (isDataChanged) {
                    emit(DataState.NewData(freshUserProfile))
                }
            }
        } catch (e: Exception) {
            Log.e("FollowedArtistsUseCase", "Error fetching followed artists", e)
            val error = customErrorHandling(e)
            emit(DataState.Error(error))
        }
    }.flowOn(Dispatchers.IO)

}