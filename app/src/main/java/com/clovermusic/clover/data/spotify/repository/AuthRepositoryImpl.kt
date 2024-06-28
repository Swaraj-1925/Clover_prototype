package com.clovermusic.clover.data.spotify.repository

import com.clovermusic.clover.data.spotify.auth.ApiScopes
import com.clovermusic.clover.data.spotify.auth.AuthConfig
import com.clovermusic.clover.domain.spotify.repository.AuthRepository

/**
 * Repository implementation from AuthRepository interface in .
 */
class AuthRepositoryImpl : AuthRepository {
    override val clientId: String = AuthConfig.CLIENT_ID
    override val requestCode: Int = AuthConfig.REQUEST_CODE
    override val redirectUri: String = AuthConfig.REDIRECT_URI
    override fun authScopes(): Array<String> {
        return ApiScopes.getAllScopes()
    }
}