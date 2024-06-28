package com.clovermusic.clover.domain.spotify.useCase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import com.clovermusic.clover.data.spotify.persistence.TokenManager
import com.clovermusic.clover.domain.spotify.repository.AuthRepository
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
){

    //    Create an Authentication request
    private fun authenticateUser(): AuthorizationRequest {
        val builder = AuthorizationRequest.Builder(
            repository.clientId,
            AuthorizationResponse.Type.TOKEN,
            repository.redirectUri
        ).setScopes(repository.authScopes())
        return builder.build()
    }

    //    Create and intent which will redirect to spotify or browser
    fun spotifyAuthIntent(activity: Activity): Intent {
        val request = authenticateUser()
        return AuthorizationClient.createLoginActivityIntent(activity, request)
    }



    //  Check the response from spotify and save token if positive else return error
    fun handleAuthResponse(
        result: ActivityResult,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val response = AuthorizationClient.getResponse(result.resultCode, result.data)
        when (response.type) {
            AuthorizationResponse.Type.TOKEN -> {
                val accessToken = response.accessToken
                tokenManager.saveAccessToken( accessToken)
                onSuccess()
                Log.i("SpotifyAuthUseCase","TOKEN RECEIVED")
            }
            AuthorizationResponse.Type.ERROR -> {
                val error = response.error
                onError(error)
            }
            else -> {
                val token = tokenManager.getAccessToken()
                if (!token.isNullOrBlank()) {
                    onSuccess()
                } else {
                    onError("Unknown error")
                }
            }
        }
    }
}