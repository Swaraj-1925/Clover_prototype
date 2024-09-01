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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.presentation.navigation.ArtistScreenRoute

@Composable
fun ArtistSearchCard(
    artistName : ArtistsEntity,
    url: String,
    navController: NavController
) {
    Card(
        colors = CardDefaults.cardColors(Color.DarkGray),
        onClick = { navController.navigate(ArtistScreenRoute(id =artistName.artistId )) },
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
                    .clip(shape = CircleShape)
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = artistName.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(shape = CircleShape)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = artistName.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .align(Alignment.Start)
                )

            }
        }
    }
}
