package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clovermusic.clover.ui.theme.CloverTheme


@Composable
fun PlaylistCard(
    url: String,
    playlistName: String,
    songCount: Int,
    onNameClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
            .clickable { onCardClick() }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = playlistName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = playlistName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { onNameClick() }
                )
                Text(
                    text = "$songCount Songs",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,

                    )
            }
        }
    }
}

@Preview
@Composable
fun Test3() {
    CloverTheme(darkTheme = true) {
        PlaylistCard(
            url = "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da849d1c40eef499de6d2957877d",
            playlistName = "Ingajisddfvfvfvf gani",
            songCount = 97,
            onCardClick = { /*TODO*/ },
            onNameClick = { /*TODO*/ })

    }
}