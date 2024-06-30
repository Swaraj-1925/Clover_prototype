package com.clovermusic.clover

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.clovermusic.clover.data.spotify.persistence.TokenManager
import com.clovermusic.clover.presentation.LatestReleasesScreen
import com.clovermusic.clover.presentation.viewModel.UserViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var tokenManager: TokenManager
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloverTheme {
                LatestReleasesScreen(userViewModel)
            }
        }


        // Check if it's the first launch and there's no access token
        val token = tokenManager.getAccessToken().toBoolean()
        Log.d("MainActivity", "Don't have AccessToken: $token")
        if (token) {
            // No access token, navigate to SpotifyAuthActivity
            startActivity(Intent(this, SpotifyAuthActivity::class.java))
            finish()
        }
    }
}


