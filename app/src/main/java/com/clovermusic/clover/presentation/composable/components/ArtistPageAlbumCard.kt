package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.clovermusic.clover.R


@Composable
fun ArtistPageAlbumCard(
    url: String,
    playlistName: String,
    songCount: Int,
    onNameClick: () -> Unit,
    onCardClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .width(420.dp)
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp, horizontal = 16.dp)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .shadow(10.dp, RoundedCornerShape(16.dp)) // Apply shadow for elevation
                    .clip(RoundedCornerShape(16.dp)) // Ensure the image is clipped to the shape
            ){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    contentDescription = playlistName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(top= 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = playlistName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.background,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .clickable { onNameClick() }
                    )
                    Text(
                        text = "$songCount Songs",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.background,
                        )
                }
                IconButton(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)) {
                    Image(painter = painterResource(id = R.drawable.more_vertical),
                        contentDescription = "More" )

                }
            }
        }
    }
}
