package com.clovermusic.clover.presentation.composable.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clovermusic.clover.presentation.composable.components.BottomBar
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.uiState.HomeScreenState
import com.clovermusic.clover.presentation.viewModel.HomeViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import com.clovermusic.clover.util.Resource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onPlaylistClick: (String) -> Unit,
    onPlaylistNameClick: (String) -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeUiState by viewModel.homeUiState.collectAsState()
    val isRefreshing by viewModel.homeUiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val pullRefresh = rememberPullRefreshState(
        refreshing = isRefreshing is Resource.Loading,
        onRefresh = {
            viewModel.refreshHomeScreen()
        }
    )
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BottomBar()
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
                        .pullRefresh(pullRefresh),
                ) {
                    HomeContent(
                        data = state.data!!,
                        onPlaylistClick = onPlaylistClick,
                        onPlaylistNameClick = onPlaylistNameClick,
                        contentPadding = paddingValues
                    )
                    PullRefreshIndicator(
                        isRefreshing is Resource.Loading,
                        pullRefresh,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}


@Composable
private fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    data: HomeScreenState,
    onPlaylistClick: (String) -> Unit,
    onPlaylistNameClick: (String) -> Unit,
    contentPadding: PaddingValues
) {
    CloverTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = contentPadding,
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