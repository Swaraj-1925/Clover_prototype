package com.clovermusic.clover

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.clovermusic.clover.presentation.ErrorAuthScreen
import com.clovermusic.clover.presentation.SpotifyAuthScreen
import com.clovermusic.clover.presentation.viewModel.AuthViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpotifyAuthActivity : ComponentActivity(){


    private lateinit var authResultLauncher: ActivityResultLauncher<Intent>
    private val viewModel : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloverTheme {
                SpotifyAuthScreen (onConnectClick = { startSpotifyAuthentication() } )
            }

        }
        /*
       this Function expects and output from the SpotifyAuthentication activity when it gets the result it
       calls the handleAuthResponse from spotifyAuthUseCase on Success it will navigate to MainActivity
        */
        authResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.handleAuthResponse(result, this,
                onSuccess = { navigateToMainAppScreen() },
                onError = { error -> showErrorScreen(error) }
            )
        }
    }


    /**
     * Start Spotify authentication flow.
     */
    private fun startSpotifyAuthentication() {
        val intent = viewModel.getSpotifyAuthIntent(this)
        authResultLauncher.launch(intent)
    }

    /**
     * Navigate to the main app screen (MainActivity).
     */
    private fun navigateToMainAppScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Show error screen if some error occurred.
     */

    private fun showErrorScreen(error: String) {
        setContent {
            CloverTheme {
                ErrorAuthScreen(text = "Some error has occurred: $error")
            }
        }
    }
}