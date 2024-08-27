package com.clovermusic.clover.presentation.composable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.presentation.navigation.PlaylistScreenRoute

@Composable
fun PlaylistSearchCard(
    playlist: PlaylistInfoEntity,
    songCount: Int,
    navController: NavController,
) {
    Card(
        colors = CardDefaults.cardColors(Color.DarkGray),
        onClick = { navController.navigate(PlaylistScreenRoute(id = playlist.playlistId))} ,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(80.dp)
                .padding(8.dp)
                .background(Color.Transparent)
        ) {
            Card(
                onClick = {  },
                modifier = Modifier
                    .padding(start = 4.dp)
                    .aspectRatio(1f)
                    .weight(0.2f)
            ) {
                AsyncImage(
                    model = playlist.imageUrl,
                    contentDescription = playlist.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = playlist.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .align(Alignment.Start)
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





