package com.clovermusic.clover

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.presentation.navigation.Navigation
import com.clovermusic.clover.ui.theme.CloverTheme
import com.clovermusic.clover.util.CustomException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: SpotifyAuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            try {
                authRepository.ensureValidAccessToken()
                setContent {
                    CloverTheme {
                        Navigation()
                    }
                }
            } catch (e: CustomException) {
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