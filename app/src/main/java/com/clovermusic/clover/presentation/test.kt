package com.clovermusic.clover.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.CurrentUserPlaylist
import com.clovermusic.clover.domain.usecase.playlist.CurrentUsersPlaylistsUseCase
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class test @Inject constructor(
    private val currentUsersPlaylistsUseCase: CurrentUsersPlaylistsUseCase
) : ViewModel() {
    private val _artistsState =
        MutableStateFlow<Resource<List<CurrentUserPlaylist>>>(Resource.Loading())
    val artistsState: StateFlow<Resource<List<CurrentUserPlaylist>>> = _artistsState.asStateFlow()

    init {
        getFollowedArtists()
    }

    private fun getFollowedArtists() {
        viewModelScope.launch {
            currentUsersPlaylistsUseCase().collect { resource ->
                _artistsState.value = resource
            }
        }
    }
}