package com.clovermusic.clover.presentation.composable.homeScreen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.R
import com.clovermusic.clover.domain.model.Albums
import com.clovermusic.clover.presentation.composable.components.NewReleaseCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun NewReleasesSection(
    onArtistClick: () -> Unit,
    onPlayClick: () -> Unit,
    onSettingsClick: () -> Unit,
    albums: List<Albums>
) {
    val albumsSize by remember { derivedStateOf { albums.size.coerceAtLeast(1) } }
    val pagerState = rememberPagerState(initialPage = 0) { albumsSize }
    var artistName by remember { mutableStateOf("Loading..") }

    val settingsButton = painterResource(id = R.drawable.settings)

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(
                nextPage,
                animationSpec = tween(durationMillis = 500),
            )
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                if (albums.isNotEmpty() && page < albums.size) {
                    artistName = albums[page].artists.firstOrNull()?.name ?: "Unknown Artist"
                }
            }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxHeight(0.5f)
            .padding(vertical = 8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.24f)
                    .padding(top = 26.dp)
            ) {
                Column(
                    horizontalAlignment = AbsoluteAlignment.Left,
                    modifier = Modifier
                        .fillMaxWidth(0.84f)
                        .fillMaxHeight()
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "New release by",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = artistName,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .clickable { onArtistClick() }
                    )
                }
                IconButton(
                    onClick = { onSettingsClick() },
                    modifier = Modifier
                        .fillMaxSize(0.67f)
                        .align(Alignment.CenterVertically)
                        .padding(4.dp)
                ) {
                    Icon(
                        painter = settingsButton,
                        contentDescription = "Settings button",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }

            HorizontalPager(
                contentPadding = PaddingValues(horizontal = 8.dp),
                state = pagerState,
                pageSpacing = 16.dp,
                userScrollEnabled = true,
            ) { currentPage ->
                NewReleaseCard(
                    album = albums[currentPage],
                    onPlayClick = { onPlayClick() }
                )
            }
        }
    }
}
