package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
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
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar
import com.clovermusic.clover.presentation.uiState.ArtistDataUiState
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.ArtistViewModal
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.util.DataState


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArtistPage(
    navController: NavController,
    artistId: String,
    viewModel: ArtistViewModal = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
) {
    val artistData by viewModel.artistData.collectAsStateWithLifecycle()
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()
    val isLoading = artistData is DataState.Loading || playbackState is PlaybackState.Loading
    val snackbarHostState = remember { SnackbarHostState() }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.getArtistData(artistId = artistId, true) }
    )

    LaunchedEffect(artistId) {
        viewModel.getArtistData(artistId = artistId, false)
    }

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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .padding(innerPadding)
        ) {
            when (val state = artistData) {
                is DataState.Loading -> {
                    LoadingAnimation()
                }

                is DataState.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar(
                            message = state.message ?: "An error occurred",
                            duration = SnackbarDuration.Long
                        )
                    }
                }

                is DataState.NewData -> {
                    ArtistContent(
                        artistInfo = state.data,
                        snackbarHostState = snackbarHostState,
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is DataState.OldData -> {
                    ArtistContent(
                        artistInfo = state.data,
                        snackbarHostState = snackbarHostState,
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun ArtistContent(
    artistInfo: ArtistDataUiState,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.padding(8.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                ArtistPageHeader(
                    artist = artistInfo.artistInfo,
                )
            }
            item {
                ArtistSongList(
                    trackList = artistInfo.artistTopTracks.take(5)
                )
            }
            item {
                ArtistPageAlbumSection(
                    albumList = artistInfo.artistAlbums.flatMap { it.albums },
                    artistId = artistInfo.artistInfo.artistId,
                    navController = navController
                )
            }
        }
    }
}


