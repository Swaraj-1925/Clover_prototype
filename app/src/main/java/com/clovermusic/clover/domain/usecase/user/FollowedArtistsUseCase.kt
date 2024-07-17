package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import com.clovermusic.clover.domain.mapper.toFollowedArtists
import com.clovermusic.clover.domain.model.common.TrackArtists
import javax.inject.Inject

/**
 * Gets all Artists and map them to FollowedArtists data class for UI and emits a new flow
 */
class FollowedArtistsUseCase @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(): List<TrackArtists> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.getFollowedArtists().toFollowedArtists()
        }.onFailure { e ->
            Log.e("FollowedArtistsUseCase", "Error fetching followed artists", e)
        }.getOrThrow()
    }
}