package com.clovermusic.clover.presentation.composable.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.ui.theme.CloverTheme

@Composable
fun PlaylistSection() {
    val yourPlaylist = "Your Playlist"
    val more = "More"
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = yourPlaylist)

                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = more)
                }
            }
            Row {

                PlaylistAlbumCard()
            }

        }
    }
}

@Preview
@Composable
fun PlaylistPreview() {
    CloverTheme {
    }
}