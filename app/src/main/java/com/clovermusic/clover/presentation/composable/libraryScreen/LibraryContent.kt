package com.clovermusic.clover.presentation.composable.libraryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.presentation.composable.components.PlaylistCard

@Composable
fun LibraryContent(playlists: List<PlaylistInfoEntity>,navController: NavController){
    val playlistsWithSizes = playlists.assignCardSizes().sortedByDescending { it.first.numClick }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(playlistsWithSizes,  key = {it.first.playlistId}){ (playlist, size) ->
            PlaylistCard(
                playlistInfo = playlist,
                navController = navController,
                size = size
            )
        }
    }
}

fun List<PlaylistInfoEntity>.assignCardSizes(): List<Pair<PlaylistInfoEntity, Pair<Int, Int>>> {
    // Sort playlists by numClick in descending order
    val sortedPlaylists = this.sortedByDescending { it.numClick }

    return sortedPlaylists.mapIndexed { index, playlist ->
        val size = when (index) {
            0 -> 2 to 2  // Most interacted playlist gets the large card
            in 1..2 -> 2 to 1  // Next two get wide cards
            in 3..5 -> 1 to 2  // Next three get tall cards
            else -> 1 to 1  // The rest get standard cards
        }
        playlist to size
    }
}