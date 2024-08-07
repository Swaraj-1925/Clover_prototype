package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clovermusic.clover.presentation.uiState.PlaybackState


@Composable
fun PlayingSongBar(
    playbackState: PlaybackState,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {

    val songDetails = when (playbackState) {
        is PlaybackState.Playing -> playbackState.songDetails
        is PlaybackState.Paused -> playbackState.songDetails
        else -> throw IllegalStateException("Unexpected state")
    }

    val playPauseIcon =
        if (playbackState is PlaybackState.Playing) Icons.Filled.Pause else Icons.Filled.PlayArrow
    val nextButton = Icons.Filled.SkipNext
    Card(
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .fillMaxHeight(0.07f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = songDetails.image,
                contentDescription = "Album art",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF2C2C2C)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxHeight()
                        .weight(0.9f)
                ) {
                    Text(
                        text = songDetails.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFEAEAEA),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { }
                    )
                    Text(
                        text = songDetails.artist,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFCFCFC),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { }
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(0.3f)
                ) {
                    IconButton(
                        onClick = { onPlayPauseClick() },
                    ) {
                        Icon(
                            imageVector = playPauseIcon,
                            contentDescription = "Play Button",
                            tint = Color(0xFFEAEAEA),
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                    IconButton(
                        onClick = { onNextClick() },
                    ) {
                        Icon(
                            imageVector = nextButton,
                            contentDescription = "Next Button",
                            tint = Color(0xFFEAEAEA),
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                }
            }
        }
    }
}