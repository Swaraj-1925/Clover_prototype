package com.clovermusic.clover.presentation.uiState

import com.clovermusic.clover.domain.model.common.PlayingTrackDetails

sealed class PlaybackState {
    object Loading : PlaybackState()
    data class Playing(val songDetails: PlayingTrackDetails) : PlaybackState()
    data class Paused(val songDetails: PlayingTrackDetails) : PlaybackState()
    data class Error(val message: String) : PlaybackState()
}