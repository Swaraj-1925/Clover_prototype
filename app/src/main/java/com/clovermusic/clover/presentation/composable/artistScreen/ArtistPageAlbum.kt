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
import com.clovermusic.clover.presentation.composable.components.ArtistPageAlbumCard
import com.clovermusic.clover.presentation.navigation.ArtistAlbumScreenRoute

@Composable
fun ArtistPageAlbumSection(
    playlist: List<swaraj.dummy> = swaraj.playlist,
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
            playlist.take(3).forEach { item ->
                ArtistPageAlbumCard(
                    url = item.imageurl,
                    playlistName = item.name,
                    songCount = item.noofsongs,
                    onNameClick = { /*TODO*/ },
                    onCardClick = { /*TODO*/ }
                )
            }
        }
    }
}



object swaraj {
    data class dummy(val imageurl: String, val name: String, val noofsongs: Int)

    val playlist = listOf(
        dummy(
            imageurl = "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da8445b3ed21fb3f250b7553c7e0",
            name = "Pranav_rw",
            noofsongs = 30
        ),
        dummy(
            imageurl = "https://i.scdn.co/image/ab67616d00001e02ff9ca10b55ce82ae553c8228",
            name = "Pranav_rw",
            noofsongs = 30
        ),
        dummy(
            imageurl = "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da8445b3ed21fb3f250b7553c7e0",
            name = "Pranav_rw",
            noofsongs = 30
        ),
        dummy(
            imageurl = "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da8445b3ed21fb3f250b7553c7e0",
            name = "Pranav_rw",
            noofsongs = 30
        ),
        dummy(
            imageurl = "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da8445b3ed21fb3f250b7553c7e0",
            name = "Pranav_rw",
            noofsongs = 30
        ),
        dummy(
            imageurl = "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da8445b3ed21fb3f250b7553c7e0",
            name = "Pranav_rw",
            noofsongs = 30
        ),
    )
}

