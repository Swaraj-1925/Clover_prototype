package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.util.Parsers.convertSpotifyImageUriToUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val playback: RemotePlaybackHandlerUseCase
) : ViewModel() {

    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Loading)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()
    private var playbackMonitoringJob: Job? = null

    init {
        startPlaybackMonitoring()
    }

    fun playPlaylist(playlistId: String) {
        viewModelScope.launch {
            try {
                playback.playMusic(playlistId)
                Log.d("HomeViewModel", "playPlaylist: Success")
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error playing playlist", e)
                _playbackState.value = PlaybackState.Error("Error playing playlist: ${e.message}")
            }
        }
    }

    fun skipToNext() {
        viewModelScope.launch {
            try {
                playback.skipToNext()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error skipping to next", e)
                _playbackState.value = PlaybackState.Error("Error skipping to next: ${e.message}")
            }
        }
    }

    fun togglePlayPause() {
        viewModelScope.launch {
            try {
                val currentState = _playbackState.value
                if (currentState is PlaybackState.Playing) {
                    playback.pauseMusic()
                    _playbackState.value = PlaybackState.Paused(currentState.songDetails)
                } else if (currentState is PlaybackState.Paused) {
                    playback.resumeMusic()
                    _playbackState.value = PlaybackState.Playing(currentState.songDetails)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error toggling play/pause", e)
                _playbackState.value =
                    PlaybackState.Error("Error toggling play/pause: ${e.message}")
            }
        }
    }

    private fun startPlaybackMonitoring() {
        Log.d("HomeViewModel", "Starting playback monitoring")
        playbackMonitoringJob = viewModelScope.launch {
            while (isActive) {
                try {
                    updatePlaybackState()
                    delay(1000)
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error in playback monitoring", e)
                    _playbackState.value =
                        PlaybackState.Error("Playback monitoring error: ${e.message}")
                }
            }
            Log.d("HomeViewModel", "Playback monitoring stopped")
        }
    }

    private suspend fun updatePlaybackState() {
        playback.performRemoteAction { remote ->
            remote.playerApi.playerState.setResultCallback { playerState ->
                val trackDetails = playerState.track.let { track ->
                    PlayingTrackDetails(
                        name = track.name,
                        songUri = track.uri,
                        artists = track.artist.name,
                        artistsUri = track.artist.uri,
                        image = track.imageUri.raw!!.convertSpotifyImageUriToUrl()
                    )
                }

                _playbackState.value = if (playerState.isPaused) {
                    PlaybackState.Paused(trackDetails)
                } else {
                    PlaybackState.Playing(trackDetails)
                }
            }
        }
    }

    private fun refreshPlaybackState() {
        viewModelScope.launch {
            try {
                updatePlaybackState()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error refreshing playback state", e)
                _playbackState.value =
                    PlaybackState.Error("Error refreshing playback state: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        playbackMonitoringJob?.cancel()
    }
}