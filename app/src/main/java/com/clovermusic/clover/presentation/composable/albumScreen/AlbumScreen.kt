package com.clovermusic.clover.presentation.composable.albumScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.ArtistAndAlbumViewModal
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.util.DataState

@Composable
fun AlbumScreen(
    albumViewModal: ArtistAndAlbumViewModal = hiltViewModel(),
    albumId: String,
    navController: NavController,

    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()
) {
    Log.d("AlbumScreen", "AlbumScreen: $albumId")
    LaunchedEffect(albumId) {
        albumViewModal.getAlbum(albumId)
    }
    val albumData by albumViewModal.album.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val playbackState by musicPlayerViewModel.musicPlayerState.collectAsStateWithLifecycle()
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
                        onClick = { /*TODO*/ },
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
                .padding(innerPadding)
        ) {
            when (val state = albumData) {
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

//                    it contain AlbumEntity
                   val albumStateData = state.data.album
//                    it contain Track Entity
                    val trackStateData = state.data.tracks
                AlbumScreenContent(
                    albumInfo = albumStateData,
                    trackList = trackStateData,)
                }

                is DataState.OldData -> {
                    val albumStateData = state.data.album
//                    it contain Track Entity
                    val trackStateData = state.data.tracks
                    AlbumScreenContent(
                        albumInfo = albumStateData,
                        trackList = trackStateData,)
                }
            }
        }


    }
}