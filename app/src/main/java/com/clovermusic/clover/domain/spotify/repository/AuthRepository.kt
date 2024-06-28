package com.clovermusic.clover.domain.spotify.repository

/**
 * Repository to hold Header of Spotify API
 */
interface AuthRepository {
    val clientId: String
    val requestCode: Int
    val redirectUri: String
    fun authScopes(): Array<String>
}