package com.clovermusic.clover.presentation.uiState

sealed class MusicPlayerUiState {
    data object Playing : MusicPlayerUiState()
    data object Paused : MusicPlayerUiState()
    data object Stopped : MusicPlayerUiState()
    data class MusicError(val message: String?) : MusicPlayerUiState()
    data object Loading : MusicPlayerUiState()
}
