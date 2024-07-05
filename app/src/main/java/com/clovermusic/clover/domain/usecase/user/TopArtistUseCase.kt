package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import com.clovermusic.clover.domain.mapper.toTopArtists
import com.clovermusic.clover.domain.model.TopArtists
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopArtistUseCase @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(timeRange: String = "short_term"): Flow<Resource<List<TopArtists>>> =
        flow {
            emit(Resource.Loading())
            try {
                authRepository.ensureValidAccessToken(
                    onTokenRefreshed = {
                        val topArtists = repository.getTopArtists(timeRange)
                        if (topArtists.isNotEmpty()) {
                            emit(Resource.Success(toTopArtists(topArtists)))
                        } else {
                            emit(Resource.Error("No top artists found"))
                        }
                    },
                    onError = { error ->
                        emit(Resource.Error("Failed to refresh token: $error"))
                    }
                )
            } catch (e: Exception) {
                Log.e("GetTopArtistsUseCase", "Error getting data: ${e.message}")
                emit(Resource.Error("An error occurred while fetching top artists"))
            }
        }
}