package com.clovermusic.clover

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
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

    private lateinit var authResultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: SpotifyAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        authResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                viewModel.handleAuthResponse(
                    result,
                    onSuccess = { navigateToMainActivity() },
                )
            }

        setContent {
            CloverTheme(darkTheme = false) {
                SpotifyAuthScreen(
                    viewModel = viewModel,
                    onConnectClick = { viewModel.buildSpotifyAuthRequestAndGetIntent(this) },
                    onTermsClick = {},
                )
            }
        }

    }

    /**
     * Start Spotify authentication flow.
     */
    private fun startSpotifyAuthentication() {

    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
