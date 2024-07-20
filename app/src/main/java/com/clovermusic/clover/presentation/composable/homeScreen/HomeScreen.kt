package com.clovermusic.clover.presentation.composable.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clovermusic.clover.presentation.composable.components.ErrorSnackbar
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.NavigationBar
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
                    val playlists = state.data.currentUsersPlaylists
                    val topArtists = state.data.topArtists
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 90.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                NewReleasesSection(
                                    albums = albums,
                                    onArtistClick = { /*TODO*/ },
                                    onPlayClick = { /*TODO*/ },
                                    onSettingsClick = { /*TODO*/ }
                                )
                            }
                            item {
                                PlaylistSection(
                                    onPlaylistClick = { /*TODO*/ },
                                    onPlaylistNameClick = { /*TODO*/ },
                                    playlists = playlists
                                )
                            }
                            item {
                                TopArtistsSection(
                                    artists = topArtists,
                                    onArtistClick = { /*TODO*/ }
                                )
                            }
                        }
                        NavigationBar(
                            onHomeClick = { /*TODO*/ },
                            onSearchClick = { /*TODO*/ },
                            onLibraryClick = { /*TODO*/ },
                            onProfileClick = { /*TODO*/ }
                        )
                    }
                }
            }
        }
    }
}
