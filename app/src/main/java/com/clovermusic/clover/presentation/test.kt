package com.clovermusic.clover.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.PlaylistItem
import com.clovermusic.clover.domain.usecase.playlist.PlaylistItemsUseCase
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class test @Inject constructor(
    private val playlistItemsUseCase: PlaylistItemsUseCase
) : ViewModel() {
    private val _artistsState =
        MutableStateFlow<Resource<List<PlaylistItem>>>(Resource.Loading())
    val artistsState: StateFlow<Resource<List<PlaylistItem>>> = _artistsState.asStateFlow()

    init {
        getFollowedArtists()
    }

    private fun getFollowedArtists() {
        viewModelScope.launch {
            playlistItemsUseCase("3KmOjYMdaijoiW5L1BPI2h").collect { resource ->
                _artistsState.value = resource
            }
        }
    }
}