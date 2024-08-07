package com.clovermusic.clover.domain.usecase.auth

import android.util.Log
import androidx.activity.result.ActivityResult
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.CustomException
import javax.inject.Inject

class HandleSpotifyAuthIntentResponseUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction
) {
    suspend operator fun invoke(result: ActivityResult): Boolean {
        return runCatching {
            val res = networkDataAction.authData.handleAuthResponse(result)
            res
        }.onFailure { e ->
            Log.e("HandleSpotifyAuthIntentResponseUseCase", "Error:", e)
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