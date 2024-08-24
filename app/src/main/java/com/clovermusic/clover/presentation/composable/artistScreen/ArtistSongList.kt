package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.data.local.entity.relations.TrackWithArtists
import com.clovermusic.clover.presentation.composable.components.SongListCard

@Composable
fun ArtistSongList(
    trackList: List<TrackWithArtists>,
    modifier: Modifier = Modifier,

    ) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val items = listOf(
        TabItem(title = "Songs"),
        TabItem(title = "Albums")
    )

    val pagerState = rememberPagerState{
        items.size
    }
    LaunchedEffect(selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    Column(
        modifier= Modifier
            .fillMaxWidth()
    ) {
        TabRow(selectedTabIndex=selectedTabIndex){
            items.forEachIndexed{index,item->
                Tab(selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(text = item.title)
                    })

            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                ) { index ->
            when(index){
                0 -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier
                            .height(320.dp)
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
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(trackList) { track ->
                                SongListCard(
                                    track = track.track,
                                    artists = track.artists,
                                    index = trackList.indexOf(track) + 1
                                )
                            }
                        }
                    }
                }
                1 -> {
                }
            }
        }
    }

}

data class TabItem(
    val title: String
)