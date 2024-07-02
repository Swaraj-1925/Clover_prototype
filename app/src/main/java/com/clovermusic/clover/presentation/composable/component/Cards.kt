package com.clovermusic.clover.presentation.composable.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.R
import com.clovermusic.clover.ui.theme.CloverTheme
import kotlinx.coroutines.delay

// Card for new album or songs section on home screen
@Composable
fun NewReleaseCard(images: List<Int>) {
    val playButton = painterResource(id = R.drawable.play)
    val name = "Name album"

    val pagerState = rememberPagerState(pageCount = { images.size })
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.wrapContentSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.wrapContentSize()
            ) { currentPage ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(horizontal = 14.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = Modifier.height(184.dp)
                    ) {
                        Image(
                            painter = painterResource(id = images[currentPage]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Text(
                            text = name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(4.dp)
                                .wrapContentSize()
                                .background(
                                    Color.Black.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        )
                        Icon(
                            painter = playButton,
                            contentDescription = "Play Button",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(50.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
            }
        }

        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until pageCount) {
            IndicatorDots(isSelected = i == currentPage, modifier = Modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier = Modifier) {
    val size by animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    val color by animateColorAsState(
        targetValue = if (isSelected) Color(0xff373737) else Color(
            0xA8373737
        ), label = ""
    )

    Box(
        modifier = modifier
            .padding(2.dp)
            .width(size)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color)
    )
}

@Composable
fun PlaylistAlbumCard() {
    val image = painterResource(id = R.drawable.ablum1)
    val playButton = painterResource(id = R.drawable.play)
    val more = painterResource(id = R.drawable.more_vertical)
    val title = "Name album"
    val description = "68 songs"

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.Gray,
        modifier = Modifier
            .padding(end = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .size(width = 150.dp, height = 200.dp)
        ) {
            Card {
                Box {
                    Image(
                        painter = image,
                        contentDescription = null
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                        ) {
                        Icon(
                            painter = more,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(4.dp)
                                .wrapContentSize()
                                .background(
                                    Color.White.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                ){
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = playButton,
                        contentDescription = null
                    )
                }
            }
        }
    }

}

@Composable
fun SongCard() {
    val image = painterResource(id = R.drawable.ablum1)
    val playButton = painterResource(id = R.drawable.playtriangle)
    val title = "Name song"
    val description = "Name artiest"

    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(180.dp)
            .padding(8.dp)
            .size(width = 180.dp, height = 210.dp)
    ) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier

            )
            Icon(
                painter = playButton,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(24.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(0.7f)
                    .padding(4.dp)
                    .background(
                        Color.White.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(7.dp)
                    )
                    .padding(7.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )

            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun TestA() {
    val images = listOf(
        R.drawable.ablum1,
        R.drawable.clover_spotify_png,
        R.drawable.playtriangle,
        R.drawable.settings,
        R.drawable.more_vertical,
    )
    CloverTheme(darkTheme = false) {
        PlaylistAlbumCard()
    }
}

