package com.clovermusic.clover.presentation.composable.playlistScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.clovermusic.clover.presentation.composable.components.ErrorSnackbar
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.viewModel.PlaylistViewModel
import com.clovermusic.clover.util.Parsers.convertMillisToHoursMinutes
import com.clovermusic.clover.util.Resource

@Composable
fun PlaylistScreen(
    playlistId: String,
    viewModel: PlaylistViewModel = hiltViewModel()
) {
    val playlistUiState = viewModel.playlistUiState.collectAsState()
    LaunchedEffect(playlistId) {
        viewModel.getPlaylist(playlistId)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val state = playlistUiState.value) {
            is Resource.Loading -> {
                LoadingAnimation()
            }

            is Resource.Error -> {
                ErrorSnackbar(
                    message = state.message.toString(),
                    snackbarHostState = snackbarHostState
                )
            }

            is Resource.Success -> {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val playlist = state.data!!
                    val playlistName = playlist.name
                    val playlistDescription = playlist.description
                    val playlistNumberSongs = playlist.tracks.size
                    val playlistFollowers = playlist.followers
                    val playlistOwner = playlist.owner.display_name
                    val playlistImage = playlist.image[0].url
                    var playlistDuration = 0
                    playlist.tracks.forEach {
                        playlistDuration += it.durationMs
                    }
                    val formattedDuration = convertMillisToHoursMinutes(playlistDuration)
                    PlaylistHeader(
                        playlistName = playlistName,
                        playlistDescription = playlistDescription,
                        playlistNumberSongs = playlistNumberSongs,
                        playlistFollowers = playlistFollowers,
                        playlistOwner = playlistOwner,
                        playlistImage = playlistImage,
                        playlistTime = formattedDuration
                    )
                }
            }
        }
    }
}