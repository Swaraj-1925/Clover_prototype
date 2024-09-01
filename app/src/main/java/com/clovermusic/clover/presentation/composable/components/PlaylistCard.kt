package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.presentation.navigation.PlaylistScreenRoute
import com.clovermusic.clover.presentation.viewModel.LibraryViewModel
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel


@Composable
fun PlaylistCard(
   playlistInfo: PlaylistInfoEntity,
    size: Pair<Int, Int> = 1 to 1,
    navController: NavController,
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
   libraryViewModel: LibraryViewModel = hiltViewModel()
) {
    val (widthFactor, heightFactor) = size

    Card(
        onClick = {
            musicPlayerViewModel.playTrack(playlistInfo.uri)
            libraryViewModel.incrementNumClick(playlistInfo.playlistId)
                  },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .width(160.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            AsyncImage(
                model = playlistInfo.imageUrl,
                contentDescription = playlistInfo.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp *heightFactor)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = playlistInfo.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .clickable { navController.navigate(PlaylistScreenRoute(id = playlistInfo.playlistId)) }
                )
                Text(
                    text = "${playlistInfo.totalTrack} Songs",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,

                    )
            }
        }
    }
}
