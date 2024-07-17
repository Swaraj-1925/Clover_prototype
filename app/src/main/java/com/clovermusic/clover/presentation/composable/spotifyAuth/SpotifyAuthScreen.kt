package com.clovermusic.clover.presentation.composable.spotifyAuth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clovermusic.clover.R
import com.clovermusic.clover.presentation.composable.components.ErrorSnackbar
import com.clovermusic.clover.presentation.composable.components.LoadingAnimation
import com.clovermusic.clover.presentation.viewModel.SpotifyAuthViewModel
import com.clovermusic.clover.util.Resource


@Composable
fun SpotifyAuthScreen(
    viewModel: SpotifyAuthViewModel,
    onConnectClick: () -> Unit,
    onTermsClick: () -> Unit,
) {
    val uiState by viewModel.authUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AuthScreenHeader()
                AutScreenBody()
                AuthScreenFooter(
                    onConnectClick = onConnectClick,
                    onTermsClick = onTermsClick
                )
            }
        }
        if (uiState is Resource.Loading) {
            LoadingAnimation()
        } else if (uiState is Resource.Error) {
            ErrorSnackbar(
                message = uiState.message.toString(),
                snackbarHostState = snackbarHostState
            )
        }

    }
}

@Composable
fun AuthScreenHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(start = 20.dp, top = 30.dp, bottom = 30.dp)
        ) {
            Text(
                text = "Clover",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Discover Music \nEffortlessly",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier
            )

        }
//        horizontal lines
        /*
                Column(
                    horizontalAlignment = AbsoluteAlignment.Right,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    repeat(5) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(
                                    fraction = when (index) {
                                        0, 4 -> 0.8f
                                        1 -> 1f
                                        2 -> 0.3f
                                        3 -> 0.6f
                                        else -> 1f
                                    }
                                )
                                .height(12.dp)
                                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                                .background(Color.Black)
                        )
                    }
                }

         */
    }
}

@Composable
fun AutScreenBody() {
    val isDarkTheme = isSystemInDarkTheme()
    val cloverSpotifyLogo = if (isDarkTheme) {
        painterResource(id = R.drawable.clover_spotify_png_white)
    } else {
        painterResource(id = R.drawable.clover_spotify_png)
    }
    val radio = painterResource(id = R.drawable.radio)
    val description = stringResource(id = R.string.welcome_message)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 40.dp)
    ) {
        Image(
            painter = cloverSpotifyLogo,
            contentDescription = "Clover Spotify logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.8f)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 8.dp)
        )
        Image(
            painter = radio,
            contentDescription = "Radio image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxHeight(0.5f)
        )
    }
}

@Composable
fun AuthScreenFooter(
    onConnectClick: () -> Unit,
    onTermsClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Button(
            onClick = {
                onConnectClick()

            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Connect to spotify",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
        Text(
            text = "By Clicking on this button you will accept all the terms and condition",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { onTermsClick() }
        )
    }
}