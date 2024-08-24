package com.clovermusic.clover.presentation.composable.artistScreens

import androidx.compose.foundation.layout.Column
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
import com.clovermusic.clover.presentation.composable.artistScreen.TabItem

@Composable
fun ArtistScreenBody() {
    val titles = listOf(
        TabItem(title = "Songs"),
        TabItem(title = "Albums")
    )

    val pagerState = rememberPagerState {
        titles.size
    }

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }
    Column {

        TabRow(selectedTabIndex = selectedTabIndex) {
            titles.forEachIndexed { index, item ->
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
        ) { index ->


        }
    }
}