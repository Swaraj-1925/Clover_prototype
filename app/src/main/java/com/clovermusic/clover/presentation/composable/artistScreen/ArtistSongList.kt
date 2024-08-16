package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.presentation.composable.components.SongListCard

@Composable
fun ArtistSongList(
    playlist: Playlist,
    modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) }
    val tracks = remember(playlist.tracks) { playlist.tracks }
    val listState = rememberLazyListState()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .height(if (expanded.value) 320.dp else 160.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 20.dp, end = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Top Songs",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = if (expanded.value) "Show Less" else "See More",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.clickable { expanded.value = !expanded.value }
            )
        }

        LazyColumn(
            state = listState
        ) {
            val tracksToShow = if (expanded.value) tracks else tracks.take(3)
            itemsIndexed(tracksToShow, key = { _, track -> track.track.trackId }) { index, track ->
                SongListCard(
                    track = track.track,
                    artists = track.artists,
                    index = index + 1
                )
            }
        }
    }
}
