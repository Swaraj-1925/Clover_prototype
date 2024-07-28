package com.clovermusic.clover.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.Playlist
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
// Class is Set up to manage UI of playlist fetch them from PlayListUseCases
@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases
) : ViewModel() {
    private val _playlistUiState = MutableStateFlow<Resource<Playlist>>(Resource.Loading())
    val playlistUiState: StateFlow<Resource<Playlist>> = _playlistUiState.asStateFlow()

    fun getPlaylist(id: String) {
        viewModelScope.launch {
            _playlistUiState.value = Resource.Loading()
            _playlistUiState.value = try {
                Resource.Success(playlistUseCases.getPlaylist(playlistId = id))
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}