package com.clovermusic.clover.util

import android.content.Context
import android.content.pm.PackageManager
import com.clovermusic.clover.BuildConfig

object SpotifyAuthConfig {
    const val CLIENT_ID: String = BuildConfig.SPOTIFY_CLIENT_ID             // Stores Spotify client id
    const val CLIENT_SECRET: String = BuildConfig.SPOTIFY_CLIENT_SECRET     // Stored Client Secret retrieved from BuildConfig
    const val REQUEST_CODE: Int = 1337
    const val REDIRECT_URI: String = "clovermusicapp://callback"
// Check whether spotify is installed on the device or not
    fun isSpotifyInstalled(context: Context): Boolean {
        val spotifyPackage = "com.spotify.music"
        return try {
            context.packageManager.getPackageInfo(spotifyPackage, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}