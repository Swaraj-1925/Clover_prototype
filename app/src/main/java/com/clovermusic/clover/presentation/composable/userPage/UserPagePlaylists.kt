package com.clovermusic.clover.presentation.composable.userPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.presentation.composable.components.PlaylistCard

@Composable
fun UserPagePlaylistSection(playlist: List<swaraj.dummy> = swaraj.playlist) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(4.dp)
    ) {
        items(playlist.size) { index ->
            val item = playlist[index]
            PlaylistCard(
                url = item.imageurl,
                playlistName = item.name,
                songCount = item.noofsongs,
                onNameClick = { /*TODO*/ },
                onCardClick = { /*TODO*/ }
            )
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
        dummy(
            imageurl = "https://image-cdn-ak.spotifycdn.com/image/ab67706c0000da8445b3ed21fb3f250b7553c7e0",
            name = "Pranav_rw",
            noofsongs = 30
        ),
    )
}

@Preview
@Composable
fun UserPagePlaylistSectionPreview() {
    UserPagePlaylistSection()
}