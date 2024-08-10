package com.clovermusic.clover.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.util.Parsers.convertSpotifyImageUriToUrl
import com.spotify.protocol.types.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
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


    val playbackPosition: StateFlow<Long> = playbackHandler.playbackPosition


    private val _isUserInteractingWithSlider = MutableStateFlow(false)
    val isUserInteractingWithSlider: StateFlow<Boolean> = _isUserInteractingWithSlider.asStateFlow()


    init {
        viewModelScope.launch {
            observePlayerState()
            playbackHandler.initialize()
        }
    }

    fun skipToNext() = playbackHandler.skipToNext()
    fun skipToPrevious() = playbackHandler.skipToPrevious()
    fun shuffleMusic() = playbackHandler.toggleShuffle()
    fun repeatTrack() = playbackHandler.toggleRepeat()
    fun playTrack(uri: String) = playbackHandler.playMusic(uri)
    fun pauseTrack() = playbackHandler.pauseMusic()
    fun resumeTrack() = playbackHandler.resumeMusic()
    fun togglePausePlay() {
        when (musicPlayerState.value) {
            is PlaybackState.Playing -> playbackHandler.pauseMusic()
            is PlaybackState.Paused -> playbackHandler.resumeMusic()
            else -> {}
        }
    }

    fun seekTo(position: Long) {
        viewModelScope.launch {
            playbackHandler.seekTo(position)
        }
    }


    fun onSliderInteractionStart() {
        _isUserInteractingWithSlider.value = true
    }

    fun onSliderInteractionEnd() {
        _isUserInteractingWithSlider.value = false
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            playbackHandler.playerState.collect { state ->
                state?.let {
                    updatePlaybackState(it)
                }
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
                duration = track.duration,
                currentPosition = playerState.playbackPosition,
                isShuffling = playerState.playbackOptions.isShuffling,
                repeatMode = playerState.playbackOptions.repeatMode
            )
        }

        _musicPlayerState.value = if (playerState.isPaused) {
            PlaybackState.Paused(trackDetails)
        } else {
            PlaybackState.Playing(trackDetails)
        }
    }
}