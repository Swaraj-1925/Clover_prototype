package com.clovermusic.clover.presentation.composable.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.clovermusic.clover.R
import com.clovermusic.clover.domain.model.common.PlayingTrackDetails
import com.clovermusic.clover.presentation.uiState.PlaybackState
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.util.Parsers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetForLyrics(
    viewModel: MusicPlayerViewModel = hiltViewModel(),
    navController: NavController
) {
    val playbackState by viewModel.musicPlayerState.collectAsStateWithLifecycle()

    var songDetails by remember { mutableStateOf<PlayingTrackDetails?>(null) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back Icon",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.height(60.dp)
            )
        },
        sheetContent = {
            Image(
                painter = painterResource(id = R.drawable.ablum1),
                contentDescription = null
            )
        },
        sheetPeekHeight = 40.dp,
        sheetMaxWidth = BottomSheetDefaults.SheetMaxWidth - 24.dp,
        sheetDragHandle = {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .height(60.dp)
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
            ) {
                Text(
                    text = "Lyrics",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) {
        when (playbackState) {
            is PlaybackState.Loading -> {
                LoadingAnimation()
            }

            is PlaybackState.Playing -> {
                songDetails = (playbackState as PlaybackState.Playing).songDetails
            }

            is PlaybackState.Paused -> {
                songDetails = (playbackState as PlaybackState.Paused).songDetails
            }

            is PlaybackState.Error -> {
                Log.e("MusicPlayerScreen", (playbackState as PlaybackState.Error).message)
            }
        }
        songDetails?.let {
            MusicPlayerDataScreen(
                songDetails = it,
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}

@Composable
fun MusicPlayerDataScreen(
    songDetails: PlayingTrackDetails,
    viewModel: MusicPlayerViewModel,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = songDetails.image,
                contentDescription = songDetails.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(12.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .align(Alignment.BottomEnd)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(alpha = 0.6f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            MusicPlayerContent(viewModel = viewModel, songDetails = songDetails)
        }
    }
}

@Composable
fun MusicPlayerContent(
    viewModel: MusicPlayerViewModel,
    songDetails: PlayingTrackDetails
) {
    val icons = getThemedIcons()
    val playPauseIcon = if (viewModel.isMusicPlaying()) {
        icons.pauseIcon
    } else {
        icons.playIcon
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(32.dp))
        Card(
            elevation = CardDefaults.cardElevation(20.dp),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = songDetails.image,
                contentDescription = songDetails.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = icons.addIcon),
                    contentDescription = "Add to library",
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.weight(9f)
            ) {
                Text(
                    text = songDetails.name,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .basicMarquee(
                            initialDelayMillis = 3000,
                            repeatDelayMillis = 2000,
                            spacing = MarqueeSpacing(14.dp),
                            velocity = 16.dp
                        )
                )
                Text(
                    text = songDetails.artists.joinToString(", "),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .basicMarquee(
                            initialDelayMillis = 3000,
                            repeatDelayMillis = 2000,
                            spacing = MarqueeSpacing(12.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {},
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    painter = painterResource(id = icons.shareIcon),
                    contentDescription = "Share",
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                )
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
        MusicPlayerControls(
            playPauseIcon = playPauseIcon,
            currentTrackDuration = songDetails.duration
        )
    }
}

@Composable
fun MusicPlayerControls(
    viewModel: MusicPlayerViewModel = hiltViewModel(),
    playPauseIcon: Int,
    currentTrackDuration: Long,
) {
    val icons = getThemedIcons()

    val playbackPosition by viewModel.playbackPosition.collectAsState()
    val isUserInteracting by viewModel.isUserInteractingWithSlider.collectAsState()

    var sliderPosition by remember { mutableStateOf(0f) }
    val maxSliderValue = currentTrackDuration.toFloat()

    LaunchedEffect(playbackPosition, isUserInteracting) {
        if (!isUserInteracting) {
            sliderPosition = playbackPosition.toFloat()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(0.85f)
    ) {
        Slider(
            value = sliderPosition,
            onValueChange = { newValue ->
                viewModel.onSliderInteractionStart()
                sliderPosition = newValue
            },
            onValueChangeFinished = {
                viewModel.onSliderInteractionEnd()
                viewModel.seekTo(sliderPosition.toLong())
            },
            valueRange = 0f..maxSliderValue
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Parsers.formatDuration(playbackPosition),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = Parsers.formatDuration(currentTrackDuration),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { viewModel.shuffleMusic() },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = icons.shuffleButtonInactive),
                    contentDescription = "Shuffle Button",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            IconButton(
                onClick = { viewModel.skipToPrevious() },
                modifier = Modifier
                    .weight(2f)
            ) {
                Icon(
                    painter = painterResource(id = icons.previousButton),
                    contentDescription = "Previous Button",
                    modifier = Modifier
                        .fillMaxSize(0.7f)
                )
            }
            IconButton(
                onClick = { viewModel.togglePausePlay() },
                modifier = Modifier
                    .weight(2f)
            ) {
                Icon(
                    painter = painterResource(id = playPauseIcon),
                    contentDescription = "Play/Pause Button",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            IconButton(
                onClick = { viewModel.skipToNext() },
                modifier = Modifier
                    .weight(2f)
            ) {
                Icon(
                    painter = painterResource(id = icons.nextButton),
                    contentDescription = "Next Button",
                    modifier = Modifier
                        .fillMaxSize(0.7f)
                )
            }
            IconButton(
                onClick = { viewModel.repeatTrack() },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = icons.repeatButtonInactive),
                    contentDescription = "Repeat Button",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

