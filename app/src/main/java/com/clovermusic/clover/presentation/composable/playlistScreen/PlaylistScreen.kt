package com.clovermusic.clover.presentation.composable.playlistScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.presentation.composable.components.BottomSheetOption
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.MoreBottomSheet
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.composable.components.PlayingSongBar
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.LibraryViewModel
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun PlaylistScreen(
    playlistId: String,
    viewModel: LibraryViewModel = hiltViewModel(),
    navController: NavController
) {
    val playlistUiState by viewModel.playlistUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()

    var bottomSheetItem by remember { mutableStateOf<BottomSheetItem?>(null) }
    var bottomSheetOptions by remember { mutableStateOf<List<BottomSheetOption>>(emptyList()) }

    LaunchedEffect(playlistId) {
        viewModel.getPlaylist(playlistId)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            when {
                bottomSheetItem?.track != null -> {
                    MoreBottomSheet(
                        track = bottomSheetItem?.track!!,
                        artists = bottomSheetItem?.artists ?: emptyList(),
                        options = bottomSheetOptions
                    )
                }
                bottomSheetItem?.playlist != null -> {
                    MoreBottomSheet(
                        track = TrackEntity(
                            trackId = bottomSheetItem?.playlist?.playlistId ?: "",
                            albumId = bottomSheetItem?.playlist?.playlistId ?: "",
                            uri = bottomSheetItem?.playlist?.uri ?: "",
                            durationMs = 0,
                            name = bottomSheetItem?.playlist?.name ?: "",
                            imageUrl = bottomSheetItem?.playlist?.imageUrl ?: "",
                            previewUrl = null,
                            timestamp = System.currentTimeMillis()
                        ),
                        artists = emptyList(),
                        options = bottomSheetOptions
                    )
                }
                else -> { /* Empty bottom sheet */ }
            }
        },
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        content = {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.padding(top = 24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                                    contentDescription = "Back Icon",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.padding(top = 24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search Icon",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier.height(60.dp)
                    )
                },
                bottomBar = {
                    Column {
                        if (playbackState is PlaybackState.Playing || playbackState is PlaybackState.Paused) {
                            PlayingSongBar(navController = navController)
                        }
                        NavigationBar(navController = navController)
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (val state = playlistUiState) {
                        is DataState.Loading -> LoadingAnimation()
                        is DataState.Error -> {
                            LaunchedEffect(state) {
                                snackbarHostState.showSnackbar(state.message ?: "An error occurred")
                            }
                        }
                        is DataState.NewData, is DataState.OldData -> {
                            val data = if (state is DataState.NewData) state.data else (state as DataState.OldData).data
                            PlaylistContent(
                                playlist = data,
                                modifier = Modifier.fillMaxSize(),
                                onMoreClick = { item, options ->
                                    bottomSheetItem = item
                                    bottomSheetOptions = options
                                    scope.launch {
                                        sheetState.show()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}