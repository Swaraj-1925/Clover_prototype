package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clovermusic.clover.ui.theme.CloverTheme

@Composable
fun PlaylistCard(
    url: String,
    playlistName: String,
    songCount: Int,
    onCardClick: () -> Unit,
    onNameClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.6f)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight()
        ) {

            AsyncImage(
                model = url,
                contentDescription = playlistName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = playlistName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(80.dp)
                        .clickable { onNameClick() }
                )
                Text(
                    text = "$songCount Songs",
                    style = MaterialTheme.typography.labelMedium,
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