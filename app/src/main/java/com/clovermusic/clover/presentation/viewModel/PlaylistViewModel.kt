package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.data.providers.playlist.Playlist
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.util.Resource
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
    private val playback: RemotePlaybackHandlerUseCase
) : ViewModel() {
    private val _playlistUiState = MutableStateFlow<Resource<Playlist>>(Resource.Loading())
    val playlistUiState: StateFlow<Resource<Playlist>> = _playlistUiState.asStateFlow()
    private var spotifyAppRemote: SpotifyAppRemote? = null


    fun getPlaylist(id: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _playlistUiState.value = Resource.Loading()
            _playlistUiState.value = try {
                Resource.Success(
                    playlistUseCases.getPlaylist(
                        playlistId = id,
                        forceRefresh = forceRefresh
                    )
                )
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

    fun playPlaylist(playlistId: String) {
        viewModelScope.launch {
            try {
                playback.playMusic(playlistId)
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error playing playlist", e)
            }
        }
    }
}