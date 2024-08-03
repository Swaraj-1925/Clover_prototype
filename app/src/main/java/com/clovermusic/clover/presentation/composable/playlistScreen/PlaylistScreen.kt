package com.clovermusic.clover.presentation.composable.playlistScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.NavigationBar
import com.clovermusic.clover.presentation.viewModel.PlaylistViewModel
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.launch


@Composable
fun PlaylistScreen(
    playlistId: String,
    viewModel: PlaylistViewModel = hiltViewModel(),
//    onBackClick: () -> Unit
) {
    val playlistUiState by viewModel.playlistUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(playlistId) {
        viewModel.getPlaylist(playlistId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back Icon",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.background,
                elevation = 8.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        },
        bottomBar = {
            NavigationBar(
                onHomeClick = { /*TODO*/ },
                onSearchClick = { /*TODO*/ },
                onLibraryClick = { /*TODO*/ }) {

            }
        },
    ) { innerPadding ->
        when (val state = playlistUiState) {
            is Resource.Loading -> LoadingAnimation()
            is Resource.Error -> {
                LaunchedEffect(state) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(state.message ?: "An error occurred")
                    }
                }
            }

            is Resource.Success -> {
                PlaylistContent(
                    playlist = state.data,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

