package com.clovermusic.clover.presentation

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.Resource
import com.spotify.sdk.android.auth.AuthorizationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SpotifyAuthViewModel @Inject constructor(
    private val repository: SpotifyAuthRepository
) : ViewModel() {

    var authorizationRequest: AuthorizationRequest? = null

    init {
        buildSpotifyAuthRequest()
    }

    private fun buildSpotifyAuthRequest() {
        viewModelScope.launch {
            repository.buildSpotifyAuthRequest().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        authorizationRequest = resource.data
                    }

                    is Resource.Error -> {
                        Log.e(
                            "SpotifyAuthViewModel",
                            "Error building auth request: ${resource.message}"
                        )
                    }

                    is Resource.Loading -> {
                    }
                }
            }
        }
    }

    fun handleAuthResponse(
        result: ActivityResult,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.handleAuthResponse(result, onSuccess, onError)
        }
    }
}
