package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.domain.usecase.app.AppUseCases
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.util.Parsers.convertSpotifyImageUriToUrl
import com.clovermusic.clover.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
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

    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.Loading)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private var playbackMonitoringJob: Job? = null

    init {
        Log.d("HomeViewModel", "Initializing HomeViewModel")
        getHomeScreen()
        startPlaybackMonitoring()
    }

    fun playPlaylist(playlistId: String) {
        viewModelScope.launch {
            try {
                playback.playMusic(playlistId)
                Log.d("HomeViewModel", "playPlaylist: Success")
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error playing playlist", e)
                _playbackState.value = PlaybackState.Error("Error playing playlist: ${e.message}")
            }
        }
    }

    fun skipToNext() {
        viewModelScope.launch {
            try {
                playback.skipToNext()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error skipping to next", e)
                _playbackState.value = PlaybackState.Error("Error skipping to next: ${e.message}")
            }
        }
    }

    fun togglePlayPause() {
        viewModelScope.launch {
            try {
                val currentState = _playbackState.value
                if (currentState is PlaybackState.Playing) {
                    playback.pauseMusic()
                    _playbackState.value = PlaybackState.Paused(currentState.songDetails)
                } else if (currentState is PlaybackState.Paused) {
                    playback.resumeMusic()
                    _playbackState.value = PlaybackState.Playing(currentState.songDetails)
                }
//                refreshPlaybackState()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error toggling play/pause", e)
                _playbackState.value =
                    PlaybackState.Error("Error toggling play/pause: ${e.message}")
            }
        }
    }

    fun refreshHomeScreen() {
        getHomeScreen()
        refreshPlaybackState()
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

    private fun startPlaybackMonitoring() {
        Log.d("HomeViewModel", "Starting playback monitoring")
        playbackMonitoringJob = viewModelScope.launch {
            while (isActive) {
                try {
                    updatePlaybackState()
                    delay(1000)
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error in playback monitoring", e)
                    _playbackState.value =
                        PlaybackState.Error("Playback monitoring error: ${e.message}")
                }
            }
            Log.d("HomeViewModel", "Playback monitoring stopped")
        }
    }

    private suspend fun updatePlaybackState() {
        playback.performRemoteAction { remote ->
            remote.playerApi.playerState.setResultCallback { playerState ->
                val trackDetails = playerState.track.let { track ->
                    PlayingTrackDetails(
                        name = track.name,
                        songUri = track.uri,
                        artists = track.artist.name,
                        artistsUri = track.artist.uri,
                        image = track.imageUri.raw!!.convertSpotifyImageUriToUrl()
                    )
                }

                _playbackState.value = if (playerState.isPaused) {
                    PlaybackState.Paused(trackDetails)
                } else {
                    PlaybackState.Playing(trackDetails)
                }
            }
        }
    }

    private fun refreshPlaybackState() {
        viewModelScope.launch {
            try {
                updatePlaybackState()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error refreshing playback state", e)
                _playbackState.value =
                    PlaybackState.Error("Error refreshing playback state: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        playbackMonitoringJob?.cancel()
    }
}
