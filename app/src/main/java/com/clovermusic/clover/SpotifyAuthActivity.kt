package com.clovermusic.clover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.clovermusic.clover.presentation.composable.spotifyAuth.SpotifyAuthScreen
import com.clovermusic.clover.presentation.viewModel.SpotifyAuthViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * Activity for spotify authentication,
 * this activity is separate from main activity because this requires to create an intent
 * and to keep the main activity clean its separate
 */

@AndroidEntryPoint
class SpotifyAuthActivity : ComponentActivity() {

    private val viewModel: SpotifyAuthViewModel by viewModels()
    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.handleAuthResponse(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            CloverTheme(darkTheme = false) {
                SpotifyAuthScreen(
                    onConnectClick = {
                        viewModel.buildSpotifyAuthRequestAndGetIntent(
                            this,
                            authLauncher
                        )
                    },
                    onTermsClick = {},
                )
            }
        }

    }

}
