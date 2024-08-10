package com.clovermusic.clover.presentation.composable.components

import android.util.Log
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.presentation.navigation.MusicPlayerScreenRoute
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun PlayingSongBar(
    viewModel: MusicPlayerViewModel = hiltViewModel(),
    navController: NavController
) {
    val playbackState by viewModel.musicPlayerState.collectAsStateWithLifecycle()

    val songDetails: PlayingTrackDetails? = when (playbackState) {
        is PlaybackState.Loading -> {
            // Display a loading indicator or state
            LoadingAnimation()
            null
        }

        is PlaybackState.Playing -> {
            (playbackState as PlaybackState.Playing).songDetails
        }

        is PlaybackState.Paused -> {
            (playbackState as PlaybackState.Paused).songDetails
        }

        is PlaybackState.Error -> {
            // Display an error state
            Log.e("MiniPlayerBar", (playbackState as PlaybackState.Error).message)
            null
        }
    }


    val skipNextSwipe = SwipeAction(
        onSwipe = {
            viewModel.skipToNext()
        },
        icon = {
            Icon(
                Icons.Default.SkipNext,
                contentDescription = "Delete chat",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        background = MaterialTheme.colorScheme.background
    )

    val skipPreviousSwipe = SwipeAction(
        onSwipe = {
            viewModel.skipToPrevious()
        },
        icon = {
            Icon(
                Icons.Default.SkipPrevious,
                contentDescription = "Delete chat",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        background = MaterialTheme.colorScheme.background
    )
    SwipeableActionsBox(
        modifier = Modifier,
        swipeThreshold = 30.dp,
        startActions = listOf(skipPreviousSwipe),
        endActions = listOf(skipNextSwipe)
    ) {

        if (songDetails != null) {
            val playPauseIcon =
                if (playbackState is PlaybackState.Playing) Icons.Filled.Pause else Icons.Filled.PlayArrow

            val nextButton = Icons.Filled.SkipNext

            Card(
                onClick = { navController.navigate(MusicPlayerScreenRoute) },
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
                                .weight(0.5f)
                                .fillMaxHeight()

                        ) {
                            Text(
                                text = songDetails.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFFEAEAEA),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .clickable { }
                                    .basicMarquee(
                                        initialDelayMillis = 3000,
                                        repeatDelayMillis = 2000,
                                        spacing = MarqueeSpacing(14.dp),
                                        velocity = 16.dp
                                    )
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
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .weight(0.3f)
                        ) {
                            IconButton(
                                onClick = { viewModel.togglePausePlay() },
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
                                onClick = { viewModel.skipToNext() },
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
    }
}