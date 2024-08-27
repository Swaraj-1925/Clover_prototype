package com.clovermusic.clover.presentation.composable.libraryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.LibraryViewModel
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.util.DataState

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
    navController: NavController
) {
    val playlistInfo by libraryViewModel.playlistInfo.collectAsStateWithLifecycle()
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()

    val categories by libraryViewModel.categories.collectAsStateWithLifecycle()
    val selectedCategories by libraryViewModel.selectedCategories.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategories.contains(category),
                            onClick = { libraryViewModel.onCategorySelected(category) },
                            label = { Text(category) },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            },
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
            floatingActionButton = {
                    Button(
                        onClick = {/*TODO*/ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Create Playlist",
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary
                                )
                        )
                    }
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                when (val state = playlistInfo) {
                    is DataState.Error -> {
                        LaunchedEffect(snackbarHostState) {
                            snackbarHostState.showSnackbar(
                                message = state.message,
                                duration = SnackbarDuration.Long
                            )
                        }
                    }

                    is DataState.Loading -> LoadingAnimation()
                    is DataState.NewData -> {

//                    Contain  List<PlaylistInfoEntity> Similar to playlist Section in Home Screen
                        val playlistInfo = state.data
                        LibraryContent(
                            playlists = playlistInfo,
                            navController = navController
                        )
                    }

                    is DataState.OldData -> {
                        val playlistInfo = state.data
                        LibraryContent(
                            playlists = playlistInfo,
                            navController = navController
                        )
                    }
                }
            }
        }
    }