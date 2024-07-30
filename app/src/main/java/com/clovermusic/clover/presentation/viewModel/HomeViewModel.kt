package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.usecase.app.AppUseCases
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
    private val appUseCases: AppUseCases,
    private val userUseCases: UserUseCases,
    private val playback: RemotePlaybackHandlerUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<Resource<HomeScreenState>>(Resource.Loading())
    val homeUiState: StateFlow<Resource<HomeScreenState>> = _homeUiState.asStateFlow()


    private var playbackMonitoringJob: Job? = null

    init {
        Log.d("HomeViewModel", "Initializing HomeViewModel")
        getHomeScreen()
    }

    fun refreshHomeScreen() {
        getHomeScreen()
    }

    private fun getHomeScreen() {
        viewModelScope.launch {
            _homeUiState.value = Resource.Loading()
            try {
                val homeScreenState = fetchHomeScreenData()
                _homeUiState.value = Resource.Success(homeScreenState)
                Log.d("HomeViewModel", "fetchHomeScreenData: Success ${homeUiState.value.data}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "fetchHomeScreenData: Error", e)
                _homeUiState.value = Resource.Error(e.message ?: "Something went wrong")
            }
        }
    }

    private suspend fun fetchHomeScreenData(): HomeScreenState = coroutineScope {
        val currentUsersPlaylistsDeferred = async { playlistUseCases.currentUserPlaylist() }
        val followedArtistsAlbumsDeferred = async { appUseCases.latestReleasesUseCase(limit = 100) }
        val topArtistsDeferred = async { userUseCases.topArtists("medium_term") }

        val followedArtistsAlbums = followedArtistsAlbumsDeferred.await()
        val currentUsersPlaylists = currentUsersPlaylistsDeferred.await().take(5)
        val topArtists = topArtistsDeferred.await().take(10)

        HomeScreenState(
            followedArtistsAlbums = followedArtistsAlbums,
            currentUsersPlaylists = currentUsersPlaylists,
            topArtists = topArtists
        )
    }

}
