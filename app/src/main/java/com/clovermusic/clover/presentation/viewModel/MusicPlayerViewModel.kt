package com.clovermusic.clover.presentation.viewModel

import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val playback: RemotePlaybackHandlerUseCase
) {
}