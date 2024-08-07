package com.clovermusic.clover.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
) : ViewModel() {
    private val _playlistUiState = MutableStateFlow<DataState<Playlist>>(DataState.Loading)
    val playlistUiState: StateFlow<DataState<Playlist>> = _playlistUiState.asStateFlow()

    fun getPlaylist(id: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val playlist = playlistUseCases.getPlaylist(forceRefresh, id)
            playlist.collect { state ->
                when (state) {
                    is DataState.Error -> _playlistUiState.value = DataState.Error(state.message)
                    DataState.Loading -> _playlistUiState.value = DataState.Loading
                    is DataState.NewData -> {
                        _playlistUiState.value = DataState.NewData(state.data)
                    }

                    is DataState.OldData -> {
                        _playlistUiState.value = DataState.OldData(state.data)
                    }
                }
            }
        }
    }
}
