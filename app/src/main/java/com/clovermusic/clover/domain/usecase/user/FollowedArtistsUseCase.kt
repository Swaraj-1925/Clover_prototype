package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import com.clovermusic.clover.domain.mapper.Util.toTrackArtists
import com.clovermusic.clover.domain.model.common.TrackArtists
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Gets all Artists and map them to FollowedArtists data class for UI and emits a new flow
 */
class FollowedArtistsUseCase @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<TrackArtists>>> = flow {
        emit(Resource.Loading())
        try {
            authRepository.ensureValidAccessToken(
                onTokenRefreshed = {
                    val artists = repository.getFollowedArtists()
                    if (artists.isNotEmpty()) {
                        emit(Resource.Success(artists.toTrackArtists()))
                    } else {
                        Log.d("GetFollowedArtistsUseCase", "No followed artists found")
                        emit(Resource.Error("No followed artists found"))
                    }
                },
                onError = { error ->
                    Log.e("GetFollowedArtistsUseCase", "Error getting data: $error")
                    emit(Resource.Error("Failed to refresh token: $error"))
                }
            )
        } catch (e: Exception) {
            Log.e("GetFollowedArtistsUseCase", "Error getting data: ${e.message}")
            emit(Resource.Error("An error occurred while fetching followed artists"))
        }
    }
}