package com.clovermusic.clover.data.spotify.auth

import com.clovermusic.clover.BuildConfig


/**
 *  Creating Const Object for Spotify Authentication
 */
object AuthConfig {
    const val CLIENT_ID: String = BuildConfig.SPOTIFY_CLIENT_ID
    const val REQUEST_CODE: Int = 1337
    const val REDIRECT_URI: String = "clovermusicapp://callback"
}