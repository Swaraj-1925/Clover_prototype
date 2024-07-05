package com.clovermusic.clover.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResult
import com.clovermusic.clover.data.api.spotify.service.SpotifyAuthService
import com.clovermusic.clover.util.Resource
import com.clovermusic.clover.util.SpotifyApiScopes
import com.clovermusic.clover.util.SpotifyAuthConfig
import com.clovermusic.clover.util.SpotifyTokenManager
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of SpotifyAuthRepository
 */
@Singleton
class SpotifyAuthRepository @Inject constructor(
    private val tokenManager: SpotifyTokenManager,
    private val api: SpotifyAuthService,
    @ApplicationContext private val context: Context
) {

    private val clientId = SpotifyAuthConfig.CLIENT_ID
    private val clientSecret = SpotifyAuthConfig.CLIENT_SECRET
    private val redirectUri = SpotifyAuthConfig.REDIRECT_URI
    private val spotifyPackage = "com.spotify.music"


    suspend fun buildSpotifyAuthRequest(): Flow<Resource<AuthorizationRequest>> = flow {
        emit(Resource.Loading())
        try {
            val spotifyInstalled = isSpotifyInstalled()
            if (!spotifyInstalled) {
                Log.e(
                    "SpotifyAuthRepositoryImpl",
                    "buildSpotifyAuthRequest: Spotify is not installed"
                )
                emit(Resource.Error("Spotify is not installed"))
            } else {
                val authRequest = AuthorizationRequest
                    .Builder(
                        clientId,
                        AuthorizationResponse.Type.CODE,
                        redirectUri
                    )
                    .setScopes(SpotifyApiScopes.getAllScopes())
                    .build()

                emit(Resource.Success(authRequest))
            }
        } catch (e: Exception) {
            Log.e("SpotifyAuthRepositoryImpl", "buildSpotifyAuthRequest: ${e.message}", e)
            emit(Resource.Error("Unknown error. Please contact support for assistance."))
        } catch (e: IOException) {
            Log.e("SpotifyAuthRepositoryImpl", "buildSpotifyAuthRequest: ${e.message}", e)
            emit(Resource.Error("Network error occurred during authentication. Please try again later."))
        }
    }

    suspend fun handleAuthResponse(
        result: ActivityResult,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val response = AuthorizationClient.getResponse(result.resultCode, result.data)
        when (response.type) {
            AuthorizationResponse.Type.CODE -> {
                val code = response.code
                Log.i("SpotifyAuthUseCase", "CODE  RECEIVED")

                withContext(Dispatchers.IO) {
                    exchangeCodeForTokens(code, onSuccess, onError)
                }
            }

            AuthorizationResponse.Type.ERROR -> {
                val error = response.error
                onError(error)
            }

            else -> {
                onError("SpotifyAuthRepositoryImpl: Unknown response type: ${response.type}")
            }
        }
    }


    private suspend fun exchangeCodeForTokens(
        code: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = api.exchangeCodeForTokens(
                grantType = "authorization_code",
                code = code,
                redirectUri = redirectUri,
                clientId = clientId,
                clientSecret = clientSecret
            )
            tokenManager.saveAccessToken(response.access_token)
            response.refresh_token?.let { tokenManager.saveRefreshToken(it) }
            tokenManager.saveTokenExpirationTime(System.currentTimeMillis() + response.expires_in * 1000)

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onError("Failed to exchange authorization code: ${e.message}")
            }
        }
    }

    suspend fun ensureValidAccessToken(
        onTokenRefreshed: suspend () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        val accessToken = tokenManager.getAccessToken()
        if (accessToken.isNullOrBlank() || isTokenExpired(accessToken)) {
            refreshAccessToken(
                onSuccess = { onTokenRefreshed() },
                onError = { onError(it) }
            )
        } else {
            onTokenRefreshed()
        }
    }

    private suspend fun refreshAccessToken(
        onSuccess: suspend () -> Unit,
        onError: suspend (String) -> Unit
    ) {
        val refreshToken = tokenManager.getRefreshToken() ?: run {
            withContext(Dispatchers.Main) {
                onError("No refresh token available")
            }
            return
        }
        try {
            val response = api.refreshAccessToken(
                grantType = "refresh_token",
                refreshToken = refreshToken,
                clientId = clientId,
                clientSecret = SpotifyAuthConfig.CLIENT_SECRET
            )
            tokenManager.saveAccessToken(response.access_token)
            tokenManager.saveTokenExpirationTime(System.currentTimeMillis() + response.expires_in * 1000)

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onError("Failed to refresh access token: ${e.message}")
            }
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        val expirationTime = tokenManager.getTokenExpirationTime()
        return System.currentTimeMillis() > expirationTime
    }

    private fun isSpotifyInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo(spotifyPackage, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


}
