package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.providers.Providers
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import javax.inject.Inject

/**
 * Gets all Artists and map them to FollowedArtists data class for UI and emits a new flow
 */
class FollowedArtistsUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val providers: Providers
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): List<ArtistsEntity> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            providers.followedArtists(forceRefresh)
        }.onFailure { e ->
            Log.e("FollowedArtistsUseCase", "Error fetching followed artists", e)
        }.getOrThrow()
    }
}