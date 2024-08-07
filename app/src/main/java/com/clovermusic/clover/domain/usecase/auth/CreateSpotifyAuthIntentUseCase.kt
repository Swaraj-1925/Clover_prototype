package com.clovermusic.clover.domain.usecase.auth

import android.app.Activity
import android.content.Intent
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import com.spotify.sdk.android.auth.AuthorizationClient
import javax.inject.Inject

class CreateSpotifyAuthIntentUseCase @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    operator fun invoke(activity: Activity): Intent {
        return try {
            val authRequest = networkDataSource.authData.buildSpotifyAuthRequest()
            val intent = AuthorizationClient.createLoginActivityIntent(activity, authRequest)
            intent
        } catch (e: Exception) {
            throw Throwable(e)
        }
    }


}