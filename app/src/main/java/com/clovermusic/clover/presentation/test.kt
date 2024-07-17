package com.clovermusic.clover.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.common.TrackArtists
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class test @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
    private val userUseCases: UserUseCases,
    private val artistUseCases: ArtistUseCases
) : ViewModel() {
    private val _artistsState =
        MutableStateFlow<Resource<List<TrackArtists>>>(Resource.Loading())
    val artistsState: StateFlow<Resource<List<TrackArtists>>> = _artistsState.asStateFlow()

    val artist = "246dkjvS1zLTtiykXe5h60"
    val playlist = "6sSKe7i5Z42rMIoOuW75yY"

    init {
        getFollowedArtists()
    }

    private fun getFollowedArtists() {
        viewModelScope.launch {

        }
    }
}