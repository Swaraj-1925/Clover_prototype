package com.clovermusic.clover.util

import android.content.Context
import android.content.pm.PackageManager
import com.clovermusic.clover.BuildConfig

object SpotifyAuthConfig {
    const val CLIENT_ID: String = BuildConfig.SPOTIFY_CLIENT_ID
    const val REQUEST_CODE: Int = 1337
    const val REDIRECT_URI: String = "clovermusicapp://callback"

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