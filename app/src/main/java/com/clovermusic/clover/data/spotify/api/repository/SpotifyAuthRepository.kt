package com.clovermusic.clover.data.spotify.api.repository

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResult
import com.clovermusic.clover.data.spotify.api.service.SpotifyAuthService
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.SpotifyApiScopes
import com.clovermusic.clover.util.SpotifyAuthConfig
import com.clovermusic.clover.util.SpotifyTokenManager
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class for Spotify authentication.
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

    //    Builds the Spotify authorization request.
    fun buildSpotifyAuthRequest(): AuthorizationRequest {
        return runCatching {
            if (!isSpotifyInstalled()) {
                throw CustomException.AuthException(
                    "SpotifyAuthRepository",
                    "buildSpotifyAuthRequest",
                    Throwable("Spotify is not installed")
                )
            } else {
                val authRequest = AuthorizationRequest
                    .Builder(
                        clientId,
                        AuthorizationResponse.Type.CODE,
                        redirectUri
                    )
                    .setScopes(SpotifyApiScopes.getAllScopes())
                    .build()

                authRequest
            }
        }.onFailure { e ->
            Log.e("SpotifyAuthRepository", "buildSpotifyAuthRequest: ", e)
        }.getOrThrow()
    }

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
                        throw CustomException.AuthException(
                            "SpotifyAuthRepository",
                            "handleAuthResponse",
                            Throwable(res.error)
                        )
                    }

                    AuthorizationResponse.Type.EMPTY -> {
                        throw CustomException.EmptyResponseException(
                            "SpotifyAuthRepository",
                            "handleAuthResponse"
                        )
                    }

                    else -> {
                        throw CustomException.UnknownException(
                            "SpotifyAuthRepository",
                            "handleAuthResponse",
                            "Unknown response type",
                            Throwable(res.type.name)
                        )
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is CustomException -> throw e
                    else -> throw CustomException.UnknownException(
                        "SpotifyAuthRepository",
                        "handleAuthResponse",
                        "Some error occured while handling response",
                        e
                    )
                }
            }

        }
    }

    //    Exchanges the authorization code for access and refresh tokens.
    private suspend fun exchangeCodeForTokens(
        code: String,
    ) {
        try {
            val response = api.exchangeCodeForTokens(
                grantType = "authorization_code",
                code = code,
                redirectUri = redirectUri,
                clientId = clientId,
                clientSecret = clientSecret
            )
            tokenManager.clearAllTokens()
            tokenManager.saveAccessToken(response.access_token)
            response.refresh_token?.let { tokenManager.saveRefreshToken(it) }
            tokenManager.saveTokenExpirationTime(System.currentTimeMillis() + response.expires_in * 1000)

        } catch (e: IOException) {
            throw CustomException.NetworkException(
                "SpotifyAuthRepository",
                "exchangeCodeForTokens",
                e
            )
        } catch (e: Exception) {
            throw CustomException.UnknownException(
                "SpotifyAuthRepository",
                "exchangeCodeForTokens",
                "An unexpected error occurred",
                e
            )
        }
    }

    //    Checks if the access token is valid or needs to be refreshed.
    suspend fun ensureValidAccessToken() {
        val accessToken = tokenManager.getAccessToken()
        if (isTokenExpired()) {
            refreshAccessToken()
        } else if (accessToken.isNullOrBlank()) {
            throw CustomException.AuthException(
                "SpotifyAuthRepository",
                "ensureValidAccessToken",
                Throwable("Access token is null or blank")
            )
        }
    }

    //    Refreshes the access token using the refresh token.
    private suspend fun refreshAccessToken() {
        try {
            val refreshToken = tokenManager.getRefreshToken()
                ?: throw CustomException.AuthException(
                    "SpotifyAuthRepository",
                    "refreshAccessToken",
                    Throwable("Refresh token is null")
                )
            val response = api.refreshAccessToken(
                grantType = "refresh_token",
                refreshToken = refreshToken,
                clientId = clientId,
                clientSecret = SpotifyAuthConfig.CLIENT_SECRET
            )
            tokenManager.saveAccessToken(response.access_token)
            tokenManager.saveTokenExpirationTime(System.currentTimeMillis() + response.expires_in * 1000)
        } catch (e: IOException) {
            throw CustomException.NetworkException("SpotifyAuthRepository", "refreshAccessToken", e)
        } catch (e: Exception) {
            when (e) {
                is CustomException -> throw e
                else -> throw CustomException.UnknownException(
                    "SpotifyAuthRepository",
                    "refreshAccessToken",
                    "Unknown error",
                    e
                )
            }
        }
    }

    //    Checks if the access token has expired.
    private fun isTokenExpired(): Boolean {
        val expirationTime = tokenManager.getTokenExpirationTime()
        return System.currentTimeMillis() > expirationTime
    }

    //    Checks if Spotify is installed on the device.
    private fun isSpotifyInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo(spotifyPackage, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
