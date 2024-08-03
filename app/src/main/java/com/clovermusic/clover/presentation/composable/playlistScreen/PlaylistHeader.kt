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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import coil.compose.AsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.data.providers.playlist.Playlist
import com.clovermusic.clover.util.Parsers

@Composable
fun PlaylistHeader(
    playlist: Playlist
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
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                        .weight(0.4f)
                ) {
                    AsyncImage(
                        model = playlist.playlistInfo!!.imageUrl,
                        contentDescription = playlist.playlistInfo.name,
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
                    PlaylistInfo(playlist)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Buttons()
        }
    }
}

@Composable
fun PlaylistInfo(playlist: Playlist) {
    val more = painterResource(id = R.drawable.more2)

    Text(
        text = "${playlist.tracks!!.size} songs • ${
            Parsers.parseDurationHoursMinutes(
                playlist.tracks.sumOf { it.durationMs })
        }",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondary
    )

    Text(
        text = playlist.playlistInfo!!.name,
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
            text = "${playlist.playlistInfo.owner} ${if (playlist.playlistInfo.followers > 1) " • " else ""}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        if (playlist.playlistInfo.followers < 1) {
            Text(
                text = "${playlist.playlistInfo.followers} followers",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(
            onClick = { /*TODO*/ },
        ) {
            Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = "followed",
                modifier = Modifier.size(24.dp)
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = more,
                contentDescription = "More",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun Buttons() {
    val shuffle = painterResource(id = R.drawable.shuffle1)
    val play = painterResource(id = R.drawable.play2)
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 14.dp)
            .background(color = Color.Transparent)
    ) {
        FilledTonalButton(
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
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
                    painter = shuffle,
                    contentDescription = "Shuffle button",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Shuffle",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
        Button(
            onClick = {},
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
                    painter = play,
                    contentDescription = "Play button",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Play",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}
