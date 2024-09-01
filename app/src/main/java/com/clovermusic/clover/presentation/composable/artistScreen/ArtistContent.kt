package com.clovermusic.clover.presentation.composable.artistScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clovermusic.clover.presentation.composable.components.ArtistPageAlbumCard
import com.clovermusic.clover.presentation.composable.components.BottomSheetOption
import com.clovermusic.clover.presentation.composable.components.SongListCard
import com.clovermusic.clover.presentation.composable.components.getThemedIcons
import com.clovermusic.clover.presentation.uiState.ArtistDataUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtistContent(
    artistInfo: ArtistDataUiState,
    navController: NavController,
    onMoreClick :(List<BottomSheetOption>)-> Unit

) {
    val titles = listOf("Songs", "Albums")
    val numAlbums = artistInfo.artistAlbums.flatMap { it.albums }.size

    val pagerState = rememberPagerState {
        titles.size
    }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val icons = getThemedIcons()

    val trackOption = listOf(
        BottomSheetOption(
            iconResId = icons.queueIcon,
            label = "Add to queue",
            onClick = {}
        ),
        BottomSheetOption(
            iconResId = icons.removeIcon,
            label = "Hide this track",
            onClick = {}
        ),
        BottomSheetOption(
            iconResId = icons.artistIcon,
            label = "View Artist",
            onClick = {}
        ),
        BottomSheetOption(
            iconResId = icons.albumIcon,
            label = "View Album",
            onClick = {}
        ),
        BottomSheetOption(
            iconResId = icons.shareIcon,
            label = "Share",
            onClick = {}
        ),
    )
    val albumOption = listOf(
        BottomSheetOption(
            iconResId = icons.queueIcon,
            label = "Add to queue",
            onClick = {}
        ),


        BottomSheetOption(
            iconResId = icons.shareIcon,
            label = "Share",
            onClick = {}
        ),
    )

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            ArtistScreenHeader(
                artist = artistInfo.artistInfo,
                numAlbums = numAlbums
            )
        }

        stickyHeader {
            TabRow(selectedTabIndex = selectedTabIndex) {
                titles.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = item) }
                    )
                }
            }
        }

        item {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) { pageIndex ->
                when (pageIndex) {
                    0 -> {
                        val trackList = artistInfo.artistTopTracks
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(trackList) { track ->
                                SongListCard(
                                    track = track.track,
                                    artists = track.artists,
                                    index = trackList.indexOf(track) + 1,
                                    onMoreClick = {onMoreClick(trackOption)}
                                )
                            }
                        }
                    }

                    1 -> {
                        val albumList = artistInfo.artistAlbums.flatMap { it.albums }
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(albumList) { album ->
                                ArtistPageAlbumCard(
                                    albums = album,
                                    navController = navController,
                                    onMoreClick = {onMoreClick(albumOption)}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
