package com.clovermusic.clover.presentation.composable.musicplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.uiState.SongDetails

@Composable
fun MusicPlayer(
    songDetails: SongDetails,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        SubcomposeAsyncImage(
            model = songDetails.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    AsyncImage(
                        model = songDetails.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
                item {
                    ControlButtons(
                        onPlayPauseClick = onPlayPauseClick,
                        onNextClick = onNextClick
                    )
                }
            }
        }
    }
}

@Composable
fun ControlButtons(
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { /* TODO: Previous Song */ }) {
            Icon(
                painter = painterResource(id = R.drawable.previous),
                contentDescription = "Previous"
            )
        }
        IconButton(onClick = onPlayPauseClick) {
            Icon(
                painter = painterResource(id = R.drawable.play_circle),
                contentDescription = "Play"
            )
        }
        IconButton(onClick = onNextClick) {
            Icon(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "Next"
            )
        }
        IconButton(onClick = { /* TODO: Like Song */ }) {
            Icon(
                painter = painterResource(id = R.drawable.like),
                contentDescription = "Like"
            )
        }
        IconButton(onClick = { /* TODO: Add to Playlist */ }) {
            Icon(
                painter = painterResource(id = R.drawable.following),
                contentDescription = "Add to Playlist"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MusicPlayerPreview() {
    val sampleSongDetails = SongDetails(
        image = "https://example.com/sample_image.jpg"
    )
    MusicPlayer(
        songDetails = sampleSongDetails,
        onPlayPauseClick = {},
        onNextClick = {}
    )
}
