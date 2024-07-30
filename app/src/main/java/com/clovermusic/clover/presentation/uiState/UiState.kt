package com.clovermusic.clover.presentation.uiState

import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.presentation.composable.musicplayer.SongDetails

sealed class PlaybackState {
    object Loading : PlaybackState()
    data class Playing(val songDetails: SongDetails) : PlaybackState()
    data class Paused(val songDetails: PlayingTrackDetails) : PlaybackState()
    data class Error(val message: String) : PlaybackState()
}