package com.clovermusic.clover.data.repository

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResult
import com.clovermusic.clover.util.Resource
import com.clovermusic.clover.util.SpotifyApiScopes
import com.clovermusic.clover.util.SpotifyAuthConfig
import com.clovermusic.clover.util.SpotifyTokenManager
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of SpotifyAuthRepository
 */
@Singleton
class SpotifyAuthRepository @Inject constructor(
    private val tokenManager: SpotifyTokenManager,
    @ApplicationContext private val context: Context
) {

    private val clientId = SpotifyAuthConfig.CLIENT_ID
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
                val authRequest = AuthorizationRequest.Builder(
                    clientId,
                    AuthorizationResponse.Type.TOKEN,
                    redirectUri
                ).setScopes(
                    SpotifyApiScopes.getAllScopes()
                ).build()

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

    fun handleAuthResponse(
        result: ActivityResult,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val response = AuthorizationClient.getResponse(result.resultCode, result.data)
        when (response.type) {
            AuthorizationResponse.Type.TOKEN -> {
                val accessToken = response.accessToken
                tokenManager.saveAccessToken(accessToken)
                onSuccess()
                Log.i("SpotifyAuthUseCase", "TOKEN RECEIVED")
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

    private fun isSpotifyInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo(spotifyPackage, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}
