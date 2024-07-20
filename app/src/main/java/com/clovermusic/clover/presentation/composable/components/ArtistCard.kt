package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ArtistCard(
    artistName: String,
    url: String,
    onArtistClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .width(140.dp)
            .padding(8.dp)
    ) {
        Card(
            onClick = { onArtistClick() },
            colors = CardDefaults.cardColors(Color.Transparent),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = artistName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
            )
        }
        Text(
            text = artistName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}