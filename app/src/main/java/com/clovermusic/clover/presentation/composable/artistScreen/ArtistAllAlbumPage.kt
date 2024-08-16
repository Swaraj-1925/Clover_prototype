package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clovermusic.clover.R

@Composable
fun ArtistAllAlbumPage(
    navController: NavController,
    albumId: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(vertical = 20.dp, horizontal = 8.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(id = R.drawable.back3),
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.width(76.dp))
                    Text(
                        text = "Popular Releases",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Text(
                    text = "Album",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
        // Implement the items from the playlist here if needed
        // items(playlist) { item ->
        //     ArtistPageAlbumCard(...)
        // }
    }
}


object swaraj2 {
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