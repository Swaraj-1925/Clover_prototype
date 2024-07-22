package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.Playlist
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlist: PlaylistUseCases
) : ViewModel() {
    private val _playlistUiState = MutableStateFlow<Resource<Playlist>>(Resource.Loading())
    val playlistUiState: StateFlow<Resource<Playlist>> = _playlistUiState.asStateFlow()

    fun getPlaylist(id: String) {
        viewModelScope.launch {
            _playlistUiState.value = Resource.Loading()
            try {
                val playlistDeferred = async { playlist.getPlaylist(playlistId = id) }
                val playlist = playlistDeferred.await()
                _playlistUiState.value = Resource.Success(playlist)
            } catch (e: CustomException) {
                Log.e("HomeViewModel", "getPlaylist: Error", e)
                _playlistUiState.value = Resource.Error(e.message ?: "Something went wrong")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "getPlaylist: Error", e)
                _playlistUiState.value = Resource.Error("Something went wrong")
            }
        }
    }
}