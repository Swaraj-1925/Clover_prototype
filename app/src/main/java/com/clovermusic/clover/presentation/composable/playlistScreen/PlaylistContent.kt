package com.clovermusic.clover.presentation.composable.playlistScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.presentation.composable.components.BottomSheetOption
import com.clovermusic.clover.presentation.composable.components.SongListCard
import com.clovermusic.clover.presentation.composable.components.getThemedIcons

@Composable
fun PlaylistContent(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    onMoreClick: (List<BottomSheetOption>)-> Unit
) {

    val tracks = remember(playlist.tracks) { playlist.tracks }
    val listState = rememberLazyListState()

    val icons = getThemedIcons()

    val playlistOptions = listOf(
            BottomSheetOption(
                iconResId = icons.addIcon,
                label = "Add to Playlist",
                onClick = { /* Handle Add to Playlist */ }
            ),
    BottomSheetOption(
        iconResId = icons.queueIcon,
        label = "Add to Queue",
        onClick = { /* Handle View Artist */ }
    ),
    BottomSheetOption(
        iconResId = icons.albumIcon,
        label = "View Album",
        onClick = { /* Handle View Album */ }
    ),
    BottomSheetOption(
        iconResId = icons.artistIcon,
        label = "View Artist",
        onClick = {  }
    ),
    BottomSheetOption(
        iconResId = icons.shareIcon,
        label = "Share",
        onClick = { }
    ),
    BottomSheetOption(
        iconResId = icons.removeIcon,
        label = "Remove From Playlist",
        onClick = { }
    ),

    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        state = listState
    ) {
        item {
            if (playlist != null) {
                PlaylistHeader(
                    playlist = playlist,
                    onMoreClick = onMoreClick)
            }
        }
        itemsIndexed(tracks, key = { _, track -> track.track.trackId }) { index, track ->

            SongListCard(
                track = track.track, artists = track.artists, index = index + 1,
                onMoreClick = {
                    onMoreClick(playlistOptions)
                }
            )
        }
    }
}