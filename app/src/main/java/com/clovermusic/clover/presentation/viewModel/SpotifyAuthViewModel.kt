package com.clovermusic.clover.presentation.viewModel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.usecase.auth.SpotifyAuthUseCases
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SpotifyAuthViewModel @Inject constructor(
    private val authUseCases: SpotifyAuthUseCases
) : ViewModel() {
    private val _authUiState = MutableStateFlow<Resource<Intent>>(Resource.Loading())
    val authUiState: StateFlow<Resource<Intent>> = _authUiState


    fun buildSpotifyAuthRequestAndGetIntent(
        activity: Activity,
        authLauncher: ActivityResultLauncher<Intent>
    ) {
        viewModelScope.launch {
            _authUiState.value = Resource.Loading()
            try {
                val intent = authUseCases.createIntent(activity)
                authLauncher.launch(intent)
            } catch (e: Exception) {
                _authUiState.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun handleAuthResponse(
        result: ActivityResult,
    ) {
        viewModelScope.launch {
            _authUiState.value = Resource.Loading()
            try {
                val success = authUseCases.handleAuthResponse(result)
                _authUiState.value =
                    if (success) Resource.Success() else Resource.Error("Authentication failed")
            } catch (e: Exception) {
                _authUiState.value = Resource.Error(e.message.toString())
                Log.e("SpotifyAuthViewModel", "Error: ${e.message}", e)
            }
        }
    }

}
