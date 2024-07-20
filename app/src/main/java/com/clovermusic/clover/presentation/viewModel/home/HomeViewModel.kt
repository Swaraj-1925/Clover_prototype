package com.clovermusic.clover.presentation.viewModel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.usecase.app.AppUseCases
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
    private val appUseCases: AppUseCases
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<Resource<HomeScreenState>>(Resource.Loading())
    val homeUiState: StateFlow<Resource<HomeScreenState>> = _homeUiState.asStateFlow()

    init {
        getHomeScreen()
    }

    private fun getHomeScreen() {
        viewModelScope.launch {
            _homeUiState.value = Resource.Loading()
            try {
                val homeScreenState = fetchHomeScreenData()
                _homeUiState.value = Resource.Success(homeScreenState)
                Log.d("HomeViewModel", "fetchHomeScreenData: Success ${homeUiState.value.data}")

            } catch (e: CustomException) {
                _homeUiState.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    private suspend fun fetchHomeScreenData(): HomeScreenState = coroutineScope {

//       Start Fetch followed artists and User Playlist in parallel
        val currentUsersPlaylistsDeferred = async { playlistUseCases.currentUserPlaylist() }
//        Start to fetch albums of followed artists
        val followedArtistsAlbumsDeferred = async { appUseCases.latestReleasesUseCase(limit = 200) }

//        Wait for albums and playlists to finish
        val followedArtistsAlbums = followedArtistsAlbumsDeferred.await()
        val currentUsersPlaylists = currentUsersPlaylistsDeferred.await()

        HomeScreenState(
            followedArtistsAlbums = followedArtistsAlbums,
            currentUsersPlaylists = currentUsersPlaylists
        )
    }

    fun refreshHomeScreen() {
        getHomeScreen()
    }
}
