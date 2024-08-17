package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.presentation.composable.components.ArtistPageAlbumCard
import com.clovermusic.clover.presentation.navigation.ArtistAlbumScreenRoute

@Composable
fun ArtistPageAlbumSection(
    albumList: List<AlbumEntity>,
    artistId: String = "",
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 20.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular Releases",
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(
                onClick = {
                    navController.navigate(ArtistAlbumScreenRoute(id = artistId))
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "See More",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            albumList.take(5).forEach { album ->
                ArtistPageAlbumCard(
                    albums = album,
                    songCount = albumList.size, // Assuming songCount is a property in your albums
                    navController = navController
                )
            }
        }
    }
}


