package com.clovermusic.clover.presentation.uiState

sealed class PlaybackState {
    object Loading : PlaybackState()
    object Playing : PlaybackState()
    object Paused : PlaybackState()
    data class Error(val message: String) : PlaybackState()
}