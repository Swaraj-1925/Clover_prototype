package com.clovermusic.clover

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Text
import com.clovermusic.clover.presentation.SpotifyAuthViewModel
import com.clovermusic.clover.presentation.composable.SpotifyAuthScreen
import com.clovermusic.clover.ui.theme.CloverTheme
import com.spotify.sdk.android.auth.AuthorizationClient
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
        setContent {
            CloverTheme {
                SpotifyAuthScreen(onConnectClick = { startSpotifyAuthentication() })
            }
        }
        authResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                viewModel.handleAuthResponse(
                    result,
                    onSuccess = { navigateToMainAppScreen() },
                    onError = { error -> showErrorScreen(error) }
                )
            }


    }

    /**
     * Start Spotify authentication flow.
     */
    private fun startSpotifyAuthentication() {
        val request = viewModel.authorizationRequest

        val intent =
            AuthorizationClient.createLoginActivityIntent(this@SpotifyAuthActivity, request)
        authResultLauncher.launch(intent)
    }

    private fun navigateToMainAppScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showErrorScreen(error: String) {
        setContent {
            Text(text = "some error occurred, $error")
        }
    }
}
