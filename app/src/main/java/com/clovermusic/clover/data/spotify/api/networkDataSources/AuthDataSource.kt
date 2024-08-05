package com.clovermusic.clover.data.spotify.api.networkDataSources


import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.clovermusic.clover.data.spotify.api.service.SpotifyAuthService
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.SpotifyApiScopes
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_ID
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_SECRET
import com.clovermusic.clover.util.SpotifyAuthConfig.REDIRECT_URI
import com.clovermusic.clover.util.SpotifyTokenManager
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val tokenManager: SpotifyTokenManager,
    private val api: SpotifyAuthService,
    @ApplicationContext private val context: Context
) {

    private val spotifyPackage = "com.spotify.music"

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
                        CLIENT_ID,
                        AuthorizationResponse.Type.CODE,
                        REDIRECT_URI
                    )
                    .setScopes(SpotifyApiScopes.getAllScopes())
                    .build()

                authRequest
            }
        }.onFailure { e ->
            Log.e("SpotifyAuthRepository", "buildSpotifyAuthRequest: ", e)
        }.getOrThrow()
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
    suspend fun refreshAccessToken() {
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
                clientId = CLIENT_ID,
                clientSecret = CLIENT_SECRET
            )
            tokenManager.saveAccessToken(response.access_token)
            tokenManager.saveTokenExpirationTime(System.currentTimeMillis() + response.expires_in * 1000)
        } catch (e: IOException) {
            throw CustomException.NetworkException("SpotifyAuthRepository", "refreshAccessToken", e)
        } catch (e: Exception) {
            when (e) {
                is CustomException -> throw e
                is HttpException -> {
                    if (e.code() == 400 && e.message().contains("invalid_grant")) {
                        tokenManager.clearAllTokens()
                        throw CustomException.AuthException(
                            "SpotifyAuthRepository",
                            "refreshAccessToken",
                            Throwable("Refresh token expired. User needs to re-authenticate.")
                        )
                    }
                    throw e
                }

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
        return System.currentTimeMillis() > (expirationTime - 5 * 60 * 1000)
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
