package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.providers.Providers
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import javax.inject.Inject

class TopArtistUseCase @Inject constructor(
    private val providers: Providers,
    private val authRepository: SpotifyAuthRepository
) {
    //    Get list of artist which user listen to most in short time(4 Weeks) period
    suspend operator fun invoke(
        timeRange: String = "short_term",
        forceRefresh: Boolean
    ): List<ArtistsEntity> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            providers.topArtists(forceRefresh = forceRefresh, timeRange = timeRange)
        }.onFailure { e ->
            Log.e("TopArtistUseCase", "Error fetching top artists", e)
        }.getOrThrow()
    }
}