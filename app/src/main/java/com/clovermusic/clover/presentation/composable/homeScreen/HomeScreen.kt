package com.clovermusic.clover.presentation.composable.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.clovermusic.clover.presentation.composable.components.ErrorSnackbar
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.viewModel.home.HomeViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import com.clovermusic.clover.util.Resource

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeUiState = viewModel.homeUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val state = homeUiState.value) {
            is Resource.Loading -> {
                LoadingAnimation()
            }

            is Resource.Error -> {
                ErrorSnackbar(
                    message = state.message.toString(),
                    snackbarHostState = snackbarHostState
                )
            }

            is Resource.Success -> {
                CloverTheme() {
                    val albums = state.data!!.followedArtistsAlbums
                    val playlist = state.data.currentUsersPlaylists
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        Column {
                            NewReleasesSection(
                                albums = albums,
                                onArtistClick = { /*TODO*/ },
                                onPlayClick = { /*TODO*/ },
                                onSettingsClick = { /*TODO*/ }
                            )
                            PlaylistSection(
                                onPlaylistClick = { /*TODO*/ },
                                onPlaylistNameClick = {/*TODO*/ },
                                playlists = playlist
                            )
                        }
                    }
                }
            }
        }
    }
}
