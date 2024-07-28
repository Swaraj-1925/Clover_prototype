package com.clovermusic.clover.presentation.composable.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.clovermusic.clover.presentation.uiState.MusicPlayerUiState
import com.clovermusic.clover.presentation.viewModel.HomeViewModel

@Composable
fun BottomBar(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val playerUiState by viewModel.playerUiState.collectAsState()
    LaunchedEffect(playerUiState) {
        Log.d("BottomBar", "Current playerUiState: $playerUiState")
    }
    Column {
        when (playerUiState) {
            is MusicPlayerUiState.Playing -> {
                PlayingSongBar()
            }

            else -> {
                // Optionally, you can add a Spacer here if you want to maintain layout consistency
                // Spacer(modifier = Modifier.height(8.dp))
            }
        }
        NavigationBar(
            onHomeClick = { /*TODO*/ },
            onSearchClick = { /*TODO*/ },
            onLibraryClick = { /*TODO*/ }) {
        }
    }
}