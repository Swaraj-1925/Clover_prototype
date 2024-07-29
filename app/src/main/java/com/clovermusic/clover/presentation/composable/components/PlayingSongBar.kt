package com.clovermusic.clover.presentation.composable.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails

@Composable
fun PlayingSongBar(
    songDetails: PlayingTrackDetails,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit
) {
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
            Log.i("PlayingSongCard", "image url ${songDetails.image}")
            AsyncImage(
                model = songDetails.image,
                contentDescription = "Album art",
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.4f)
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = songDetails.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = songDetails.artists,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }
            IconButton(
                onClick = { onPlayClick() },
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
                onClick = { onNextClick() },
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
