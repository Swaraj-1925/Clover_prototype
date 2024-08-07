package com.clovermusic.clover.data.spotify.api.networkDataAction

import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResult
import com.clovermusic.clover.data.spotify.api.networkDataSources.AuthDataSource
import com.clovermusic.clover.data.spotify.api.service.SpotifyAuthService
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_ID
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_SECRET
import com.clovermusic.clover.util.SpotifyAuthConfig.REDIRECT_URI
import com.clovermusic.clover.util.SpotifyTokenManager
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthDataAction @Inject constructor(
    private val tokenManager: SpotifyTokenManager,
    private val api: SpotifyAuthService,
    @ApplicationContext private val context: Context,
    private val authDataSource: AuthDataSource
) {

    //    Handles the Spotify authorization response.
    suspend fun handleAuthResponse(result: ActivityResult): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val res = AuthorizationClient.getResponse(result.resultCode, result.data)
                when (res.type) {
                    AuthorizationResponse.Type.CODE -> {
                        Log.i("SpotifyAuthRepository", "Authorization code received")
                        exchangeCodeForTokens(res.code)
                        true
                    }

                    AuthorizationResponse.Type.ERROR -> {
                        Log.e("SpotifyAuthRepository", "Error handling auth response ${res.error}")
                        throw Exception("Failed to authenticate with Spotify. Please try again.")
                    }

                    AuthorizationResponse.Type.EMPTY -> {

                        Log.e("SpotifyAuthRepository", "Error handling auth response ${res.type}")
                        throw Exception("Something went wrong while authenticating with Spotify.")
                    }

                    else -> {
                        throw Exception("Something went wrong while authenticating with Spotify.")
                    }
                }
            } catch (e: Exception) {
                throw Exception(e)
            }

        }
    }

    //    Exchanges the authorization code for access and refresh tokens.
    private suspend fun exchangeCodeForTokens(
        code: String,
    ) {
        Log.i("SpotifyAuthRepository", "Exchanging code for tokens")
        try {
            val response = api.exchangeCodeForTokens(
                grantType = "authorization_code",
                code = code,
                redirectUri = REDIRECT_URI,
                clientId = CLIENT_ID,
                clientSecret = CLIENT_SECRET
            )
            tokenManager.clearAllTokens()
            tokenManager.saveAccessToken(response.access_token)
            response.refresh_token?.let { tokenManager.saveRefreshToken(it) }
            tokenManager.saveTokenExpirationTime(System.currentTimeMillis() + response.expires_in * 1000)

        } catch (e: Exception) {
            Log.e("SpotifyAuthRepository", "Error exchanging code for tokens", e)
            throw e
        }
    }

    suspend fun ensureValidAccessToken() {
        val accessToken = tokenManager.getAccessToken()
        if (isTokenExpired()) {
            authDataSource.refreshAccessToken()
        } else if (accessToken.isNullOrBlank()) {
            throw Exception("Access token is null or blank")
        }
    }

    //    Checks if the access token has expired.
    private fun isTokenExpired(): Boolean {
        val expirationTime = tokenManager.getTokenExpirationTime()
        return System.currentTimeMillis() > (expirationTime - 5 * 60 * 1000)
    }
}