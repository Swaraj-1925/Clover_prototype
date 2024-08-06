package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.DataState
import javax.inject.Inject

class TopArtistUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: Repository
) {
    //    Get list of artist which user listen to most in short time(4 Weeks) period
    suspend operator fun invoke(
        timeRange: String = "short_term",
        forceRefresh: Boolean
    ): List<ArtistsEntity> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            var result: List<ArtistsEntity>? = null

            repository.user.getTopArtists(forceRefresh)
                .collect { dataState ->
                    result = when (dataState) {
                        is DataState.NewData -> dataState.data
                        is DataState.OldData -> dataState.data
                        is DataState.Error -> throw Exception(dataState.message)
                    }
                }

            result ?: throw Exception("Failed to fetch followed artists")
        }.onFailure { e ->
            Log.e("TopArtistUseCase", "Error fetching top artists", e)
        }.getOrThrow()
    }
}