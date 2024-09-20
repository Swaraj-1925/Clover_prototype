package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.clovermusic.clover.presentation.composable.components.getThemedIcons
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.ArtistAndAlbumViewModal
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ArtistScreen(
    navController: NavController,
    artistId: String,
    viewModel: ArtistAndAlbumViewModal = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()
) {
    LaunchedEffect(artistId) {
        viewModel.getArtistData(artistId = artistId, false, limit = 5)
    }

    val artistData by viewModel.artistData.collectAsStateWithLifecycle()
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()

    val isLoading = artistData is DataState.Loading || playbackState is PlaybackState.Loading

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var bottomSheetItem by remember { mutableStateOf<BottomSheetItem?>(null) }
    var bottomSheetOptions by remember { mutableStateOf<List<BottomSheetOption>>(emptyList()) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val icons = getThemedIcons()
    val artistOption = listOf(
        BottomSheetOption(
            iconResId = icons.artistIcon,
            label = "Follow",
            onClick = {}
        ),
        BottomSheetOption(
            iconResId = icons.removeIcon,
            label = "Don't play this artist",
            onClick = {}
        ),
        BottomSheetOption(
            iconResId = icons.shareIcon,
            label = "Share",
            onClick = {}
        ),
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
                bottomSheetItem?.album != null -> {
                    MoreBottomSheet(
                        track = TrackEntity(
                            trackId = bottomSheetItem?.artists?.firstOrNull()?.artistId ?: "",
                            albumId = bottomSheetItem?.album?.albumId ?: "",
                            uri = bottomSheetItem?.album?.uri ?: "",
                            durationMs = 0,
                            name = bottomSheetItem?.album?.name ?: "",
                            imageUrl = bottomSheetItem?.album?.imageUrl ?: "",
                            previewUrl = null,
                            timestamp = System.currentTimeMillis()
                        ),
                        artists = bottomSheetItem?.artists ?: emptyList(),
                        options = bottomSheetOptions
                    )
                }
                bottomSheetItem?.artists != null -> {
                    // This is for the artist more icon in the top app bar
                    MoreBottomSheet(
                        track = TrackEntity(
                            trackId = bottomSheetItem?.artists?.firstOrNull()?.artistId ?: "",
                            albumId = "",
                            uri = bottomSheetItem?.artists?.firstOrNull()?.uri ?: "",
                            durationMs = 0,
                            name = bottomSheetItem?.artists?.firstOrNull()?.name ?: "",
                            imageUrl = bottomSheetItem?.artists?.firstOrNull()?.imageUrl?: "",
                            previewUrl = null,
                            timestamp = System.currentTimeMillis()
                        ),
                        artists = bottomSheetItem?.artists ?: emptyList(),
                        options = bottomSheetOptions
                    )
                }
                else -> {
                    // Handle the case when no item is selected
                }
            }
        },
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding(),
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                topBar = {
                    TopAppBar(
                        title = { },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
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
                                onClick = {
                                    val artistData = (artistData as? DataState.NewData)?.data
                                        ?: (artistData as? DataState.OldData)?.data
                                    if (artistData != null) {
                                        bottomSheetItem = BottomSheetItem(
                                            artists = listOf(artistData.artistInfo)
                                        )
                                        bottomSheetOptions = artistOption
                                        scope.launch {
                                            sheetState.show()
                                        }
                                    }
                                },
                            ){
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "More Icon",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier.height(40.dp)
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
                    when (val state = artistData) {
                        is DataState.Loading -> LoadingAnimation()
                        is DataState.Error -> {
                            LaunchedEffect(snackbarHostState) {
                                snackbarHostState.showSnackbar(
                                    message = state.message,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                        is DataState.NewData, is DataState.OldData -> {
                            val data = if (state is DataState.NewData) state.data else (state as DataState.OldData).data
                            ArtistContent(
                                artistInfo = data,
                                navController = navController,
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