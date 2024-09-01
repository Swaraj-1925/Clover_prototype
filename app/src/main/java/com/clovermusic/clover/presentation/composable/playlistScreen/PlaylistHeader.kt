package com.clovermusic.clover.presentation.composable.playlistScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.presentation.composable.components.BottomSheetOption
import com.clovermusic.clover.presentation.composable.components.getThemedIcons
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.util.Parsers

@Composable
fun PlaylistHeader(
    playlist: Playlist,
    onMoreClick: (List<BottomSheetOption>) -> Unit
) {

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 250.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(12.dp),
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                        .weight(0.4f)

                ) {
                    AsyncImage(
                        model = playlist.playlist.imageUrl,
                        contentDescription = playlist.playlist.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(8.dp)
                ) {
                    PlaylistInfo(playlist,
                        onMoreClick = onMoreClick)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Buttons(playlist = playlist)
        }
    }
}

@Composable
fun PlaylistInfo(
    playlist: Playlist,
    onMoreClick: (List<BottomSheetOption>)->Unit) {
    val icons = getThemedIcons()

    val options =
        listOf(
            BottomSheetOption(
                iconResId = icons.addIcon,
                label = "Add Playlist to your Library",
                onClick = {}
            ),
            BottomSheetOption(
                iconResId = icons.queueIcon,
                label = "Add to Queue",
                onClick = {}
            ),
            BottomSheetOption(
                iconResId = icons.shareIcon,
                label = "Share Playlist",
                onClick = {}
            ),
        )
    Text(
        text = "${playlist.playlist.totalTrack} songs • ${
            Parsers.parseDurationHoursMinutes(
                playlist.tracks.sumOf { it.track.durationMs }
            )
        }",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondary
    )

    Text(
        text = playlist.playlist.name,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = "${playlist.playlist.owner}  ${if (playlist.playlist.followers > 1) " • " else ""}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        if (playlist.playlist.followers < 1) {
            Text(
                text = "${playlist.playlist.followers} followers",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = { /*TODO*/ },
        ) {
            Icon(
                painter = painterResource(id = icons.addIcon),
                contentDescription = "add to library",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        IconButton(onClick = {
            onMoreClick(options)
        }) {
            Icon(
                painter = painterResource(id = icons.moreIcon),
                contentDescription = "More",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun Buttons(
    viewModel: MusicPlayerViewModel = hiltViewModel(),
    playlist: Playlist
) {
    val icons = getThemedIcons()
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 14.dp)
            .background(color = Color.Transparent)
    ) {
        OutlinedButton(
            onClick = {
                viewModel.shuffleMusic()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight(0.95f)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = icons.shuffleButtonInactive),
                    contentDescription = "Shuffle button",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                Text(
                    text = "Shuffle",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 8.dp)
                )
            }
        }
        Button(
            onClick = { viewModel.playTrack(playlist.playlist.uri) },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f)
            ) {
                Icon(
                    painter = painterResource(id = icons.playIcon),
                    contentDescription = "Play button",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()

                )
                Text(
                    text = "Play",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(2f)
                )
            }
        }
    }
}
