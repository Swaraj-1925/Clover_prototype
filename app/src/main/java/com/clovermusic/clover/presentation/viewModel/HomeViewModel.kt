package com.clovermusic.clover.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.usecase.app.AppUseCases
import com.clovermusic.clover.domain.usecase.playback.RemotePlaybackHandlerUseCase
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.Resource
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.PlayerState
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

    private var spotifyAppRemote: SpotifyAppRemote? = null
    private var playbackStateJob: Job? = null

    init {
        Log.d("HomeViewModel", "Initializing HomeViewModel")
        getHomeScreen()
        connectToSpotify()
    }

    fun playPlaylist(playlistId: String) {
        viewModelScope.launch {
            try {
                spotifyAppRemote = playback.connectToRemote()
                spotifyAppRemote?.let { remote ->
                    playback.playMusic(remote, playlistId)
                } ?: throw CustomException.EmptyResponseException(
                    "SpotifyAppRemote",
                    "playPlaylist"
                )
                Log.d("HomeViewModel", "playPlaylist: Success")
            } catch (e: Exception) {
                Log.e("PlaylistViewModel", "Error playing playlist", e)
            }
        }
    }

    private fun getHomeScreen() {
        viewModelScope.launch {
            _homeUiState.value = Resource.Loading()
            try {
                val homeScreenState = fetchHomeScreenData()
                _homeUiState.value = Resource.Success(homeScreenState)
                Log.d("HomeViewModel", "fetchHomeScreenData: Success ${homeUiState.value.data}")

            } catch (e: CustomException) {
                Log.e("HomeViewModel", "fetchHomeScreenData: Error", e)
                _homeUiState.value = Resource.Error(e.message ?: "Something went wrong")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "fetchHomeScreenData: Error", e)
                _homeUiState.value = Resource.Error("Something went wrong")
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


    private fun connectToSpotify() {
        Log.d("HomeViewModel", "Attempting to connect to Spotify")
        viewModelScope.launch {
            try {
                spotifyAppRemote = playback.connectToRemote()
                if (spotifyAppRemote != null) {
                    Log.d("HomeViewModel", "Connected to Spotify successfully")
                    startPlaybackMonitoring()
                } else {
                    Log.e("HomeViewModel", "Failed to connect to Spotify: SpotifyAppRemote is null")
                    _playbackState.value = PlaybackState.Error("Failed to connect to Spotify")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error connecting to Spotify", e)
                _playbackState.value =
                    PlaybackState.Error("Error connecting to Spotify: ${e.message}")
            }
        }
    }

    private fun startPlaybackMonitoring() {
        Log.d("HomeViewModel", "Starting playback monitoring")
        playbackStateJob = viewModelScope.launch {
            while (isActive) {
                try {
                    spotifyAppRemote?.playerApi?.playerState?.setResultCallback { playerState ->
                        updatePlaybackState(playerState)
                    }
                    delay(1000) // Check every second
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error in playback monitoring", e)
                    _playbackState.value =
                        PlaybackState.Error("Playback monitoring error: ${e.message}")
                }
            }
            Log.d("HomeViewModel", "Playback monitoring stopped")
        }
    }

    private fun updatePlaybackState(playerState: PlayerState) {
        val newState = when {
            playerState.isPaused -> PlaybackState.Paused
            else -> PlaybackState.Playing
        }
        Log.d("HomeViewModel", "Playback state updated: $newState")
        _playbackState.value = newState
    }

    fun refreshPlaybackState() {
        viewModelScope.launch {
            try {
                spotifyAppRemote?.playerApi?.playerState?.setResultCallback { playerState ->
                    updatePlaybackState(playerState)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error refreshing playback state", e)
                _playbackState.value =
                    PlaybackState.Error("Error refreshing playback state: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

    fun refreshHomeScreen() {
        getHomeScreen()
    }
}
