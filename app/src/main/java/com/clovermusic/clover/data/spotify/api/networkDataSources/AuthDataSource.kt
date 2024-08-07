package com.clovermusic.clover.data.spotify.api.networkDataSources


import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.clovermusic.clover.data.spotify.api.service.SpotifyAuthService
import com.clovermusic.clover.util.SpotifyApiScopes
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_ID
import com.clovermusic.clover.util.SpotifyAuthConfig.CLIENT_SECRET
import com.clovermusic.clover.util.SpotifyAuthConfig.REDIRECT_URI
import com.clovermusic.clover.util.SpotifyTokenManager
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val tokenManager: SpotifyTokenManager,
    private val api: SpotifyAuthService,
    @ApplicationContext private val context: Context
) {

    private val spotifyPackage = "com.spotify.music"

    fun buildSpotifyAuthRequest(): AuthorizationRequest {
        return try {
            if (!isSpotifyInstalled()) {
                throw Exception("Spotify is not installed")
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
        } catch (e: Exception) {
            Log.e("SpotifyAuthRepository", "buildSpotifyAuthRequest: ", e)
            throw e
        }
    }

    //    Checks if the access token is valid or needs to be refreshed.
    suspend fun ensureValidAccessToken() {
        val accessToken = tokenManager.getAccessToken()
        if (isTokenExpired()) {
            refreshAccessToken()
        } else if (accessToken.isNullOrBlank()) {
            Log.e("SpotifyAuthRepository", "ensureValidAccessToken: Access token is null or blank")
            throw Exception(IllegalArgumentException("Access token is null or blank"))
        }
    }

    //    Refreshes the access token using the refresh token.
    suspend fun refreshAccessToken() {
        try {
            val refreshToken = tokenManager.getRefreshToken()

            if (refreshToken.isNullOrBlank()) {
                Log.e("SpotifyAuthRepository", "refreshAccessToken: Refresh token is null or blank")
                throw IllegalArgumentException("Refresh token is null or blank")
            }

            Log.d("SpotifyAuthRepository", "refreshAccessToken: Refresh token = $refreshToken")
            val response = api.refreshAccessToken(
                grantType = "refresh_token",
                refreshToken = refreshToken,
                clientId = CLIENT_ID,
                clientSecret = CLIENT_SECRET
            )
            tokenManager.saveAccessToken(response.access_token)
            tokenManager.saveTokenExpirationTime(System.currentTimeMillis() + response.expires_in * 1000)
        } catch (e: Exception) {
            Log.e("SpotifyAuthRepository", "refreshAccessToken: ", e)
            throw e
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
