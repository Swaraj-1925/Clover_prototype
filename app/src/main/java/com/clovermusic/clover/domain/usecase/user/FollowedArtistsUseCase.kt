package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.DataState
import javax.inject.Inject

/**
 * Gets all Artists and map them to FollowedArtists data class for UI and emits a new flow
 */
class FollowedArtistsUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: Repository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): List<ArtistsEntity> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            var result: List<ArtistsEntity>? = null

            repository.user.getFollowedArtists(forceRefresh).collect { dataState ->
                result = when (dataState) {
                    is DataState.NewData -> dataState.data
                    is DataState.OldData -> dataState.data
                    is DataState.Error -> throw Exception(dataState.message)
                }
            }

            result ?: throw Exception("Failed to fetch followed artists")
        }.onFailure { e ->
            Log.e("FollowedArtistsUseCase", "Error fetching followed artists", e)
        }.getOrThrow()
    }
}