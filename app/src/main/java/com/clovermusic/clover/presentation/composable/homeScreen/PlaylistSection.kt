package com.clovermusic.clover.presentation.composable.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.presentation.composable.components.PlaylistCard
import com.clovermusic.clover.presentation.viewModel.LibraryViewModel
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel

@Composable
fun PlaylistSection(
    viewModel: MusicPlayerViewModel = hiltViewModel(),
    playlistViewModel: LibraryViewModel = hiltViewModel(),
    playlists: List<PlaylistInfoEntity> = emptyList(),
    navController: NavController
) {
    val state = rememberLazyListState()
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Your GetPlaylist",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = "More",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.clickable { }
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = state
        ) {
            items(playlists) { playlist ->
                playlist.imageUrl?.let {
                    PlaylistCard(
                        playlistInfo = playlist,
                        navController = navController
                    )
                }
            }
        }
    }
}
