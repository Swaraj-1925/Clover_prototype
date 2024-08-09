package com.clovermusic.clover.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.util.Parsers.convertSpotifyImageUriToUrl
import com.spotify.protocol.types.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val playbackHandler: RemotePlaybackHandlerUseCase
) : ViewModel() {

    private val _musicPlayerState = MutableStateFlow<PlaybackState>(PlaybackState.Loading)
    val musicPlayerState: StateFlow<PlaybackState> = _musicPlayerState.asStateFlow()

    init {
        viewModelScope.launch {
            playbackHandler.initialize()
            observePlayerState()
        }
    }

    private val _playbackPosition = MutableStateFlow(0L)
    val playbackPosition: StateFlow<Long> = _playbackPosition.asStateFlow()

    fun skipToNext() = playbackHandler.skipToNext()
    fun skipToPrevious() = playbackHandler.skipToPrevious()
    fun shuffleMusic() = playbackHandler.toggleShuffle()
    fun togglePausePlay() {
        when (musicPlayerState.value) {
            is PlaybackState.Playing -> playbackHandler.pauseMusic()
            is PlaybackState.Paused -> playbackHandler.resumeMusic()
            else -> {}
        }
    }

    fun repeatTrack() = playbackHandler.toggleRepeat()
    fun playTrack(uri: String) = playbackHandler.playMusic(uri)
    fun seekTrack(position: Long) = playbackHandler.seekTo(position)

    fun getCurrentPlaybackPosition() = playbackHandler.getCurrentPlaybackPosition()
    fun getCurrentTrackDuration() = playbackHandler.getCurrentTrackDuration()

    private fun observePlayerState() {
        viewModelScope.launch {
            playbackHandler.playerState.collect { state ->
                state?.let {
                    updatePlaybackState(it)
                    if (!it.isPaused) {
                        startPositionTracking()
                    }
                }
            }
        }
    }

    private fun startPositionTracking() {
        viewModelScope.launch {
            while (musicPlayerState.value is PlaybackState.Playing) {
                _playbackPosition.value = playbackHandler.getCurrentPlaybackPosition()
                delay(100)
            }
        }
    }

    private fun updatePlaybackState(playerState: PlayerState) {
        val trackDetails = playerState.track.let { track ->
            PlayingTrackDetails(
                name = track.name,
                songUri = track.uri,
                artist = track.artist.name,
                artistUri = track.artist.uri,
                image = track.imageUri.raw?.convertSpotifyImageUriToUrl() ?: "",
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