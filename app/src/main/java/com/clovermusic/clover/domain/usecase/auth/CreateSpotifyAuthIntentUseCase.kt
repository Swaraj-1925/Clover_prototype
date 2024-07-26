package com.clovermusic.clover.domain.usecase.auth

import android.app.Activity
import android.content.Intent
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.CustomException
import com.spotify.sdk.android.auth.AuthorizationClient
import javax.inject.Inject

class CreateSpotifyAuthIntentUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository
) {
    operator fun invoke(activity: Activity): Intent {
        return runCatching {
            val authRequest = authRepository.buildSpotifyAuthRequest()
            val intent = AuthorizationClient.createLoginActivityIntent(activity, authRequest)
            intent
        }.onFailure { e ->
            val error = when (e) {
                is CustomException -> Throwable("Unable to Connect to Spotify")
                else -> Throwable("Something went wrong ")
            }
            throw error
        }.getOrThrow()
    }
}