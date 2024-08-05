package com.clovermusic.clover.presentation.composable.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar2
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.HomeViewModel
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import com.clovermusic.clover.util.Resource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onPlaylistClick: (String) -> Unit,
    onPlaylistNameClick: (id: String) -> Unit,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()

    val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()


    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeUiState is Resource.Loading,
        onRefresh = { homeViewModel.refreshHomeScreen() }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            Column {
                if (playbackState is PlaybackState.Playing || playbackState is PlaybackState.Paused) {
                    PlayingSongBar2(
                        playbackState = playbackState,
                        onPlayPauseClick = { musicPlayerViewModel.togglePausePlay() },
                        onNextClick = { musicPlayerViewModel.skipToNext() }
                    )
                }
                NavigationBar(
                    onHomeClick = { /*TODO*/ },
                    onSearchClick = { /*TODO*/ },
                    onLibraryClick = { /*TODO*/ },
                    onProfileClick = { /*TODO*/ }
                )
            }
        },
    ) { paddingValues ->
        when (val state = homeUiState) {
            is Resource.Loading -> {
                LoadingAnimation()
            }

            is Resource.Error -> {
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(
                        message = state.message.toString(),
                        duration = SnackbarDuration.Long
                    )
                }
            }

            is Resource.Success -> {
                Box(
                    modifier = Modifier
                        .pullRefresh(pullRefreshState)
                        .padding(paddingValues)
                ) {
                    HomeContent(
                        data = state.data!!,
                        onPlaylistClick = onPlaylistClick,
                        onPlaylistNameClick = onPlaylistNameClick
                    )
                    PullRefreshIndicator(
                        refreshing = homeUiState is Resource.Loading,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeContent(
    data: HomeScreenState,
    onPlaylistClick: (String) -> Unit,
    onPlaylistNameClick: (id: String) -> Unit
) {
    CloverTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    NewReleasesSection(
                        albums = data.followedArtistsAlbums,
                        onArtistClick = { /*TODO*/ },
                        onSettingsClick = { /*TODO*/ }
                    )
                }
                item {
                    PlaylistSection(
                        onPlaylistClick = onPlaylistClick,
                        onPlaylistNameClick = onPlaylistNameClick,
                        playlists = data.currentUsersPlaylists
                    )
                }
                item {
                    TopArtistsSection(
                        artists = data.topArtists,
                        onArtistClick = { /*TODO*/ }
                    )
                }
            }
        }
    }
}
