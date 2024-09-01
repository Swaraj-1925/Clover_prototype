package com.clovermusic.clover.presentation.composable.albumScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clovermusic.clover.presentation.composable.components.BottomSheetOption
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.composable.components.MoreBottomSheet
import com.clovermusic.clover.presentation.composable.components.getThemedIcons
import com.clovermusic.clover.presentation.viewModel.ArtistAndAlbumViewModal
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val icons = getThemedIcons()
    
    val options = listOf(
        BottomSheetOption(
            iconResId = icons.shareIcon,
            label = "Share Album",
            onClick = {}
        )
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            MoreBottomSheet(options = options)
        },
        sheetDragHandle = null
    ) {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        scope.launch {
                            if (scaffoldState.bottomSheetState.isVisible) {
                                scaffoldState.bottomSheetState.hide()
                            }

                        }
                    }
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
}