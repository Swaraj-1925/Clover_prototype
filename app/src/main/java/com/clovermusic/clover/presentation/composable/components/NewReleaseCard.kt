package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.domain.model.Albums
import com.clovermusic.clover.ui.theme.CloverTheme

@Composable
fun NewReleaseCard(
    album: Albums,
    onPlayClick: () -> Unit,
) {
    val playButton = painterResource(id = R.drawable.play)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f)
    ) {
        Card {
            AsyncImage(
                model = album.image?.firstOrNull()?.url,
                contentDescription = album.albumName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .align(Alignment.BottomEnd)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.primaryContainer
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        Text(
            text = album.albumName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.7f)
                .padding(16.dp)
        )
        IconButton(
            onClick = { onPlayClick() },
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = playButton,
                contentDescription = "play button",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestA() {
    val url =
        "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da8444c4d18c2ac6c7b6e8ae8100"
    val artistName = "Post melon"
    val releaseName = "Better now"
    CloverTheme(darkTheme = true) {
    }
}