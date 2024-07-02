package com.clovermusic.clover.presentation.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.clovermusic.clover.domain.spotify.helper.Authorization
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authorization: Authorization
) : ViewModel() {
    /**
     * Function for getting the intent for Spotify authentication builder.
     */
    fun getSpotifyAuthIntent(activity: Activity): Intent {
        return authorization.spotifyAuthIntent(activity)
    }

    /**
     * Function for handling the response from Spotify authentication.
     */

    fun handleAuthResponse(
        result: ActivityResult,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        authorization.handleAuthResponse(result, context, onSuccess, onError)
    }
}