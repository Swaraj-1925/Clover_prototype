package com.clovermusic.clover

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.presentation.navigation.Navigation
import com.clovermusic.clover.presentation.viewModel.MusicPlayerViewModel
import com.clovermusic.clover.ui.theme.CloverTheme
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var spotifyAppRemote: SpotifyAppRemote? = null

    @Inject
    lateinit var networkDataAction: NetworkDataAction
    val viewModel: MusicPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            try {
                networkDataAction.authData.ensureValidAccessToken()
                setContent {
                    CloverTheme {
                        Navigation()
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "onCreate: ${e.message}")
                navigateToSpotifyAuth()
            }
        }
    }

    private fun navigateToSpotifyAuth() {
        val intent = Intent(this, SpotifyAuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
            spotifyAppRemote = null
        }
    }

}