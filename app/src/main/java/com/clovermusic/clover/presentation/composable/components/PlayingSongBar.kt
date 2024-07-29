package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails

@Composable
fun PlayingSongBar(
    songDetails: PlayingTrackDetails,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit
) {

    val pauseButton = Icons.Filled.Pause
    val nextButton = Icons.Filled.SkipNext
    Card(
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .fillMaxHeight(0.07f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            AsyncImage(
                model = songDetails.image,
                contentDescription = "Album art",
                modifier = Modifier
                    .size(55.dp)
                    .padding(start = 10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.2f)
                    .padding(start = 8.dp)
                    .weight(2f)
            ) {
                Text(
                text = songDetails.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top= 6.dp)
                    .height(28.dp)
            )
                Text(
                    text = songDetails.artists,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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
                        .size(40.dp)
                )
            }
            IconButton(
                onClick = { onNextClick() },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .padding(end = 10.dp)
            ) {
                Icon(
                    imageVector = nextButton,
                    contentDescription = "Next Button",
                    modifier = Modifier
                        .size(40.dp)

                )
            }
        }
    }
}
