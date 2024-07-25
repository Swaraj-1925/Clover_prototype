package com.clovermusic.clover.domain.usecase.auth

import androidx.activity.result.ActivityResult
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.CustomException
import javax.inject.Inject

class HandleSpotifyAuthIntentResponseUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(result: ActivityResult): Boolean {
        return runCatching {
            val res = authRepository.handleAuthResponse(result)
            res
        }.onFailure { e ->
            val errorMessage = when (e) {
                is CustomException.AuthException -> Throwable("Authentication error. Please try again.")
                is CustomException.NetworkException -> Throwable("Network error. Please check your connection and try again.")
                is CustomException.EmptyResponseException -> Throwable("Something went wrong. During Connecting to Spotify Please try again.")
                else -> Throwable("An unexpected error occurred. Please try again later.")
            }
            throw errorMessage
        }.getOrThrow()
    }
}