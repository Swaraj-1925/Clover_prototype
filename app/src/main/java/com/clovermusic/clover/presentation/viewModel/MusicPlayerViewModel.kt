package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.util.Parsers.convertSpotifyImageUriToUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val playbackHandler: RemotePlaybackHandlerUseCase
) : ViewModel() {
    private val _musicPlayerState = MutableStateFlow<PlaybackState>(PlaybackState.Loading)
    val musicPlayerState: StateFlow<PlaybackState> = _musicPlayerState.asStateFlow()

    init {
        monitorMusicState()
    }

    fun skipToNext() {
        viewModelScope.launch {
            try {
                playbackHandler.skipToNext()
            } catch (e: Exception) {
                Log.e(
                    "MusicPlayerViewModel", "skipToNext Error: \n", e
                )
                _musicPlayerState.value = PlaybackState.Error("Unable to Skip to the Next Song")

            }
        }
    }

    fun skipToPrevious() {
        viewModelScope.launch {
            try {
                playbackHandler.skipToPrevious()
            } catch (e: Exception) {
                Log.e(
                    "MusicPlayerViewModel", "skipToPrevious Error: \n", e
                )
                _musicPlayerState.value = PlaybackState.Error("Unable to Skip to the Previous Song")

            }
        }
    }

    fun shuffleMusic() {
        viewModelScope.launch {
            try {
                playbackHandler.toggleShuffle()
            } catch (e: Exception) {
                Log.e(
                    "MusicPlayerViewModel", "shuffleMusic Error: \n", e
                )
                _musicPlayerState.value = PlaybackState.Error("Unable to Shuffle Songs")
            }
        }
    }

    fun togglePausePlay() {
        viewModelScope.launch {
            try {
                val currentState = _musicPlayerState.value
                if (currentState is PlaybackState.Playing) {
                    playbackHandler.pauseMusic()
                } else if (currentState is PlaybackState.Paused) {
                    playbackHandler.resumeMusic()
                }
            } catch (e: Exception) {
                Log.e(
                    "MusicPlayerViewModel", "togglePausePlay Error: \n", e
                )
                _musicPlayerState.value = PlaybackState.Error("Unable Play/Pause")
            }
        }
    }

    fun repeatTrack() {
        viewModelScope.launch {
            try {
                playbackHandler.toggleRepeat()
            } catch (e: Exception) {
                Log.e(
                    "MusicPlayerViewModel", "repeatTrack Error: \n", e
                )
                _musicPlayerState.value = PlaybackState.Error("Unable to Repeat Song")
            }
        }
    }

    fun playTrack(uri: String) {
        viewModelScope.launch {
            try {
                playbackHandler.playMusic(uri)
            } catch (e: Exception) {
                Log.e(
                    "MusicPlayerViewModel", "playTrack Error: \n", e
                )
                _musicPlayerState.value = PlaybackState.Error("Unable to Play Song")
            }
        }
    }

    fun seekTrack(length: Long) {
        viewModelScope.launch {
            try {
                playbackHandler.seekTo(length)
            } catch (e: Exception) {
                Log.e(
                    "MusicPlayerViewModel", "seekTrack Error: \n", e
                )
                _musicPlayerState.value = PlaybackState.Error("Unable to Seek Song")
            }
        }
    }


    private fun monitorMusicState() {
        viewModelScope.launch {
            _musicPlayerState.value = PlaybackState.Loading
            while (isActive) {
                try {
                    updatePlaybackState()
                    delay(1000)
                } catch (e: Exception) {
                    _musicPlayerState.value = PlaybackState.Error("Unable to Play Music")
                }
            }
        }
    }

    private suspend fun updatePlaybackState() {
        playbackHandler.performRemoteAction { remote ->
            remote.playerApi.playerState.setResultCallback { playerState ->
                val trackDetails = playerState.track.let { track ->
                    PlayingTrackDetails(
                        name = track.name,
                        songUri = track.uri,
                        artist = track.artist.name,
                        artistUri = track.artist.uri,
                        image = track.imageUri.raw!!.convertSpotifyImageUriToUrl(),
                        artists = track.artists.map { it.name },
                        artistsUri = track.artists.map { it.uri },
                        duration = track.duration
                    )
                }
                _musicPlayerState.value = if (playerState.isPaused) {
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
                _musicPlayerState.value =
                    PlaybackState.Error("Error refreshing playback state: ${e.message}")
            }
        }

    }
}