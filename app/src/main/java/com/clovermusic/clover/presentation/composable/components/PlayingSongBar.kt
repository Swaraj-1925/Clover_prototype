package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.R
import com.clovermusic.clover.ui.theme.CloverTheme

@Composable
fun PlayingSongBar() {
    val image = painterResource(id = R.drawable.ablum1)
    val nameSong = "Name Song"
    val nameArtist = "Name Artist"
    val playButton = Icons.Filled.PlayArrow
    val pauseButton = Icons.Filled.Pause
    val nextButton = Icons.Filled.SkipNext
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(painter = image, contentDescription = nameSong)
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.4f)
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = nameSong,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = nameArtist,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.3f)
            ) {
                Icon(
                    imageVector = pauseButton,
                    contentDescription = "Play Button",
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.3f)
            ) {
                Icon(
                    imageVector = nextButton,
                    contentDescription = "Next Button",
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                )
            }
        }
    }
}

@Preview
@Composable
fun PlayingSongBarPreview() {
    CloverTheme {
        PlayingSongBar()
    }
}