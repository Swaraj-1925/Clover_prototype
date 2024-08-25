package com.clovermusic.clover.presentation.composable.albumScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.viewModel.ArtistAndAlbumViewModal
import com.clovermusic.clover.util.DataState

@Composable
fun AlbumScreen(
    albumViewModal: ArtistAndAlbumViewModal = hiltViewModel(),
    albumId: String
) {
    Log.d("AlbumScreen", "AlbumScreen: $albumId")
    LaunchedEffect(albumId) {
        albumViewModal.getAlbum(albumId)
    }
    val albumData by albumViewModal.album.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold { innerPadding ->
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
                    state.data.album
//                    it contain Track Entity
                    state.data.tracks

                }

                is DataState.OldData -> {

                }
            }
        }


    }
}