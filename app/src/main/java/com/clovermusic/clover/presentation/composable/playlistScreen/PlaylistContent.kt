package com.clovermusic.clover.presentation.composable.playlistScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.presentation.composable.components.SongListCard

@Composable
fun PlaylistContent(
    playlist: Playlist,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            if (playlist != null) {
                PlaylistHeader(playlist = playlist)
            }
        }
        itemsIndexed(playlist.tracks) { index, track ->

            SongListCard(track = track.track, artists = track.artists, index = index + 1)
        }
    }
}