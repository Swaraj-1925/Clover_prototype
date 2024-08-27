package com.clovermusic.clover.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.domain.usecase.app.AppUseCases
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
    private val appUseCases: AppUseCases
) : ViewModel() {
    private val _playlistUiState = MutableStateFlow<DataState<Playlist>>(DataState.Loading)
    val playlistUiState: StateFlow<DataState<Playlist>> = _playlistUiState.asStateFlow()

    private val _playlistInfo = MutableStateFlow<DataState<List<PlaylistInfoEntity>>>(DataState.Loading)
    val playlistInfo: StateFlow<DataState<List<PlaylistInfoEntity>>> = _playlistInfo.asStateFlow()

    private val _categories = MutableStateFlow(listOf("Top 5", "All"))
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _selectedCategories = MutableStateFlow(setOf("Top 5"))
    val selectedCategories: StateFlow<Set<String>> = _selectedCategories.asStateFlow()


    init {
        getUserPlaylist(false)
    }
    fun getPlaylist(id: String, forceRefresh: Boolean = true) {
        viewModelScope.launch {
            val playlist = playlistUseCases.getPlaylist(forceRefresh, id)
            playlist.collect { state ->
                when (state) {
                    is DataState.Error -> _playlistUiState.value = DataState.Error(state.message)
                    is DataState.Loading -> _playlistUiState.value = DataState.Loading
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

    fun getUserPlaylist(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val info = playlistUseCases.currentUserPlaylist(forceRefresh)
            info.collect { state ->
                when (state) {
                    is DataState.Error -> _playlistInfo.value = DataState.Error(state.message)
                    is DataState.Loading -> _playlistInfo.value = DataState.Loading
                    is DataState.NewData ->{
                        val playlists = state.data
                        val filteredPlaylists = filterPlaylistsByCategory(playlists)
                        _playlistInfo.value = DataState.NewData(filteredPlaylists)}
                    is DataState.OldData -> {
                        val playlists = state.data
                        val filteredPlaylists = filterPlaylistsByCategory(playlists)
                        _playlistInfo.value = DataState.NewData(filteredPlaylists)
                    }
                }
            }
        }
    }

    fun incrementNumClick(id: String) {
        viewModelScope.launch {
            appUseCases.incrementNumClick(id)
        }
    }
    private fun filterPlaylistsByCategory(playlists: List<PlaylistInfoEntity>): List<PlaylistInfoEntity> {
        return if (selectedCategories.value.contains("All")) {
            playlists
        } else if (selectedCategories.value.contains("Top 5")) {
            playlists.sortedByDescending { it.numClick }.take(5)
        } else {
            playlists // Default case; adjust as necessary
        }
    }

    fun onCategorySelected(category: String) {
        viewModelScope.launch {
            if (category == "All") {
                _selectedCategories.value = setOf("All")
            } else {
                _selectedCategories.value = setOf(category)
            }
            getUserPlaylist(true)
        }
    }
}
