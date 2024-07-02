package com.clovermusic.clover.domain.repository

import androidx.activity.result.ActivityResult
import com.clovermusic.clover.util.Resource
import com.spotify.sdk.android.auth.AuthorizationRequest
import kotlinx.coroutines.flow.Flow

interface SpotifyAuthRepository {

    suspend fun buildSpotifyAuthRequest(): Flow<Resource<AuthorizationRequest>>
    fun handleAuthResponse(result: ActivityResult, onSuccess: () -> Unit, onError: (String) -> Unit)
    fun isSpotifyInstalled(): Boolean
}
