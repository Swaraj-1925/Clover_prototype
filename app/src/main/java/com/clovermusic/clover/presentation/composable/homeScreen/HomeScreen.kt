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
import androidx.navigation.NavController
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.HomeViewModel
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import com.clovermusic.clover.util.DataState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()

    val homeScreenState by homeViewModel.homeScreenState.collectAsStateWithLifecycle()
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()

    val isLoading = listOf(
        homeScreenState.followedArtistsAlbums,
        homeScreenState.currentUsersPlaylists,
        homeScreenState.topArtists
    ).any { it is DataState.Loading }

    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { homeViewModel.refreshHomeScreen() }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            Column {
                if (playbackState is PlaybackState.Playing || playbackState is PlaybackState.Paused) {
                    PlayingSongBar(
                        navController = navController
                    )
                }
                NavigationBar(navController = navController)
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .padding(paddingValues)
        ) {
            HomeContent(
                homeScreenState = homeScreenState,
                snackbarHostState = snackbarHostState,
                navController = navController
            )
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun HomeContent(
    homeScreenState: HomeScreenState,
    snackbarHostState: SnackbarHostState,
    navController: NavController
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
                    when (val state = homeScreenState.followedArtistsAlbums) {
                        is DataState.Loading -> {}
                        is DataState.OldData, is DataState.NewData -> {
                            val data =
                                if (state is DataState.OldData) state.data else (state as DataState.NewData).data
                            NewReleasesSection(
                                albums = data,
                                onArtistClick = { /*TODO*/ },
                                onSettingsClick = { /*TODO*/ }
                            )
                        }

                        is DataState.Error -> {
                            LaunchedEffect(snackbarHostState) {
                                snackbarHostState.showSnackbar(
                                    message = state.message,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    }
                }
                item {
                    when (val state = homeScreenState.currentUsersPlaylists) {
                        is DataState.Loading -> {}
                        is DataState.OldData -> {
                            PlaylistSection(
                                playlists = state.data.take(4),
                                navController = navController
                            )
                        }

                        is DataState.NewData -> {
                            PlaylistSection(
                                playlists = state.data.take(4),
                                navController = navController
                            )
                        }

                        is DataState.Error -> {
                            LaunchedEffect(snackbarHostState) {
                                snackbarHostState.showSnackbar(
                                    message = state.message,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    }
                }
                item {
                    when (val state = homeScreenState.topArtists) {
                        is DataState.Loading -> {}
                        is DataState.OldData, is DataState.NewData -> {
                            val data =
                                if (state is DataState.OldData) state.data else (state as DataState.NewData).data
                            TopArtistsSection(
                                artists = data.take(6),
                                onArtistClick = { /*TODO*/ }
                            )
                        }

                        is DataState.Error -> {
                            LaunchedEffect(snackbarHostState) {
                                snackbarHostState.showSnackbar(
                                    message = state.message,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
