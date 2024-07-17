package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.Resource
import com.spotify.sdk.android.auth.AuthorizationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SpotifyAuthViewModel @Inject constructor(
    private val repository: SpotifyAuthRepository
) : ViewModel() {
    private val _authUiState = MutableStateFlow<Resource<String>>(Resource.Success(""))
    val authUiState: StateFlow<Resource<String>> = _authUiState
    var authorizationRequest: AuthorizationRequest? = null


    fun buildSpotifyAuthRequest() {
        viewModelScope.launch {
            try {
                _authUiState.value = Resource.Loading()
                val authRequest = repository.buildSpotifyAuthRequest()
                authorizationRequest = authRequest

            } catch (e: CustomException.AuthException) {
                Log.e("SpotifyAuthViewModel", "Error building auth request: ${e.message}")
                _authUiState.value =
                    Resource.Error("Please make sure spotify us installed")
            } catch (e: Exception) {
                Log.e("SpotifyAuthViewModel", "Error building auth request", e)
                _authUiState.value =
                    Resource.Error("Something went wrong while Connecting to Spotify")
            }
        }
    }

    fun handleAuthResponse(
        result: ActivityResult,
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            viewModelScope.launch {
                _authUiState.value = Resource.Loading()
                try {
                    val success = repository.handleAuthResponse(result)
                    if (success) {
                        onSuccess()
                        _authUiState.value = Resource.Success("")
                    } else {
                        _authUiState.value =
                            Resource.Error("Something went wrong while connecting to Spotify")
                    }
                } catch (e: CustomException) {
                    val errorMessage = when (e) {
                        is CustomException.AuthException -> "Authentication error. Please try again."
                        is CustomException.NetworkException -> "Network error. Please check your connection and try again."
                        is CustomException.EmptyResponseException -> "Received an empty response from Spotify. Please try again."
                        else -> "An unexpected error occurred. Please try again later."
                    }
                    _authUiState.value = Resource.Error(errorMessage)
                    Log.e("SpotifyAuthViewModel", "Error: ${e.message}", e)
                }
            }
        }
    }

    fun retryAuth() {
        buildSpotifyAuthRequest()
    }
}
