package com.clovermusic.clover.presentation.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clovermusic.clover.domain.usecase.app.AppUseCases
import com.clovermusic.clover.domain.usecase.playlist.PlaylistUseCases
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playlistUseCases: PlaylistUseCases,
    private val appUseCases: AppUseCases,
    private val userUseCases: UserUseCases,
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()


    init {
        Log.d("HomeViewModel", "Initializing HomeViewModel")
        fetchHomeScreenData(forceRefresh = false)
    }

    fun refreshHomeScreen() {
        fetchHomeScreenData(forceRefresh = true)
    }

    private fun fetchHomeScreenData(forceRefresh: Boolean) {
        viewModelScope.launch {
            fetchHomeScreenDataFlow(forceRefresh).collect { newState ->
                _homeScreenState.value = newState
            }
        }
    }

    private fun fetchHomeScreenDataFlow(forceRefresh: Boolean): Flow<HomeScreenState> = flow {
        coroutineScope {

            val currentUsersPlaylistsFlow = playlistUseCases.currentUserPlaylist(forceRefresh)
            val followedArtistsAlbumsFlow = appUseCases.latestReleasesUseCase(forceRefresh, 1)
            val topArtistsFlow = userUseCases.topArtists("medium_term", forceRefresh)
            val user = async { userUseCases.getCurrentUsersProfile(forceRefresh).first() }
//            combine multiple flows and store it HomeScreenState
            combine(
                currentUsersPlaylistsFlow,
                followedArtistsAlbumsFlow,
                topArtistsFlow
            ) { playlists, albums, artists ->
                HomeScreenState(
                    followedArtistsAlbums = albums,
                    currentUsersPlaylists = playlists,
                    topArtists = artists
                )
            }.collect { newState ->
                emit(newState)
            }
        }
    }


}