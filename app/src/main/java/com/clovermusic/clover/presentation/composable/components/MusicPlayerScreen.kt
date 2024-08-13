package com.clovermusic.clover.presentation.composable.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.SkipPrevious
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
import androidx.compose.ui.graphics.vector.ImageVector
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
    val playIcon = if (isSystemInDarkTheme()) R.drawable.play_white else R.drawable.play_black
    var playPauseIcon by remember {
        mutableStateOf(
            playIcon
        )
    }
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
                        modifier = Modifier
                            .padding(top = 24.dp)
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
                modifier = Modifier
                    .height(60.dp)
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
                // Display a loading indicator or state
                LoadingAnimation()
            }

            is PlaybackState.Playing -> {
                songDetails = (playbackState as PlaybackState.Playing).songDetails
                val pauseIcon =
                    if (isSystemInDarkTheme()) R.drawable.pause_white else R.drawable.pause_black
                playPauseIcon = pauseIcon
            }

            is PlaybackState.Paused -> {
                songDetails = (playbackState as PlaybackState.Paused).songDetails

                playPauseIcon = playIcon
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
                playPauseIcon = playPauseIcon
            )
        }
    }
}


@Composable
fun MusicPlayerDataScreen(
    songDetails: PlayingTrackDetails,
    viewModel: MusicPlayerViewModel,
    playPauseIcon: ImageVector,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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

            MusicPlayerContent(
                viewModel = viewModel,
                songDetails = songDetails,
                playPauseIcon = playPauseIcon
            )
        }
    }
}

@Composable
fun MusicPlayerContent(
    viewModel: MusicPlayerViewModel,
    songDetails: PlayingTrackDetails,
    playPauseIcon: ImageVector
) {
    val addIcon = if (isSystemInDarkTheme()) R.drawable.add_white else R.drawable.add_black
    val shareIcon = if (isSystemInDarkTheme()) R.drawable.share_white else R.drawable.share_black

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
                modifier = Modifier
                    .fillMaxWidth()
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
                    .wrapContentSize()
            ) {
                Icon(
                    painter = painterResource(id = addIcon),
                    contentDescription = "Add to library",
                    tint = Color(0xFFEAEAEA)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .weight(9f)
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
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Icon(
                    painter = painterResource(id = shareIcon),
                    contentDescription = "play button",
                    tint = Color(0xFFEAEAEA)
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
    playPauseIcon: ImageVector,
    currentTrackDuration: Long,
) {
    val nextButton = if (isSystemInDarkTheme()) R.drawable.next_white else R.drawable.next_black
    val shuffleButtonInactive =
        if (isSystemInDarkTheme()) R.drawable.shuffle_white_inactive else R.drawable.shuffle_black_inactive
    val repeatButtonInactive =
        if (isSystemInDarkTheme()) R.drawable.repeat_white_inactive else R.drawable.repeat_black_inactive
    val repeatButtonActive =
        if (isSystemInDarkTheme()) R.drawable.repeat_white_active else R.drawable.repeat_black_active
    val repeatButtonActive1 =
        if (isSystemInDarkTheme()) R.drawable.repeat_white_active_2 else R.drawable.repeat_black_active_2

    val previousButton = Icons.Filled.SkipPrevious

    val playbackPosition by viewModel.playbackPosition.collectAsState()
    val isUserInteracting by viewModel.isUserInteractingWithSlider.collectAsState()

    var sliderPosition by remember { mutableStateOf(0f) }
    val maxSliderValue = currentTrackDuration.toFloat()

//    update postion only whene user is not interacting
    LaunchedEffect(playbackPosition, isUserInteracting) {
        if (!isUserInteracting) {
            sliderPosition = playbackPosition.toFloat()
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(0.85f)
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.shuffleMusic() },
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = shuffleButtonInactive),
                    contentDescription = "Shuffle Button",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            IconButton(
                onClick = { viewModel.skipToPrevious() },
                modifier = Modifier
                    .size(60.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = previousButton,
                    contentDescription = "Previous Button",
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(
                onClick = { viewModel.togglePausePlay() },
                modifier = Modifier
                    .size(60.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = playPauseIcon,
                    contentDescription = "Play Button",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            IconButton(
                onClick = { viewModel.skipToNext() },
                modifier = Modifier
                    .size(60.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = nextButton,
                    contentDescription = "Next Button",
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(
                onClick = { viewModel.repeatTrack() },
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = repeatButton,
                    contentDescription = "Repeat Button",
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}


