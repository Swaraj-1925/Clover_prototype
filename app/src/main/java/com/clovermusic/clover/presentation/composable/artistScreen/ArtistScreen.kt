package com.clovermusic.clover.presentation.composable.artistScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.illegalDecoyCallException
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    navController: NavController,
    artistId: String,
    viewModel: ArtistAndAlbumViewModal = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
    onMoreClick: (List<BottomSheetOption>)->Unit
) {
    LaunchedEffect(artistId) {
        viewModel.getArtistData(artistId = artistId, false, limit = 5)
    }
    MaterialTheme.colorScheme.background

    val artistData by viewModel.artistData.collectAsStateWithLifecycle()
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()

    val isLoading = artistData is DataState.Loading || playbackState is PlaybackState.Loading

    val snackbarHostState = remember { SnackbarHostState() }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.getArtistData(artistId = artistId, true, limit = 5) }
    )
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    var bottomSheetOptions by remember { mutableStateOf<List<BottomSheetOption>>(emptyList()) }
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false // This is the key change
        )
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
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            MoreBottomSheet(options = bottomSheetOptions)
        },
        sheetDragHandle = null
    ) {
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() },
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
                            onClick = {
                                bottomSheetOptions = artistOption
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More Icon",
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    },
                    backgroundColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .height(40.dp)
                )
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
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        scope.launch {
                            try {
                                if (sheetState.bottomSheetState.isVisible) {
                                    sheetState.bottomSheetState.hide()
                                }
                            } catch (e: Exception) {
                                Log.e("ArtistScreen", "Error hiding bottom sheet", e)
                                // Optionally show a toast or snackbar to inform the user
                            }
                        }
                    }
            )
            {
                when (val state = artistData) {
                    is DataState.Loading -> {
                        LoadingAnimation()
                    }

                    is DataState.Error -> {
                        LaunchedEffect(snackbarHostState) {
                            snackbarHostState.showSnackbar(
                                message = state.message,
                                duration = SnackbarDuration.Long
                            )
                        }
                    }

                    is DataState.NewData -> {
                        ArtistContent(
                            artistInfo = state.data,
                            navController = navController,
                            onMoreClick = { options ->
                                bottomSheetOptions = options
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        )
                    }

                    is DataState.OldData -> {
                        ArtistContent(
                            artistInfo = state.data,
                            navController = navController,
                            onMoreClick = { options ->
                                bottomSheetOptions = options
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        )
                    }
                }
            }

        }
    }
}