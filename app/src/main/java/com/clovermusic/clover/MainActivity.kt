package com.clovermusic.clover

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.presentation.navigation.Navigation
import com.clovermusic.clover.ui.theme.CloverTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkDataAction: NetworkDataAction

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
}