package com.clovermusic.clover.presentation.composable.albumScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.presentation.composable.components.ArtistPageAlbumCard
import com.clovermusic.clover.presentation.composable.components.SongListCard

@Composable
fun AlbumScreenContent(
    albumInfo : AlbumEntity,
    trackList: List<TrackEntity>
)
{
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            ArtistScreenHeader(
                album = albumInfo)
        }

        items(trackList) { track ->
            SongListCard(
                track = track,
                index = trackList.indexOf(track) + 1
            )
        }
    }
}