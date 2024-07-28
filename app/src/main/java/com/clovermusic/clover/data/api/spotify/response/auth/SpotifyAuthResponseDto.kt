package com.clovermusic.clover.data.api.spotify.response.auth
/* Used in SpotifyAuthService.kt in exchangeCodeForTokens and refreshAccessToken function.
    These function are used in SpotifyAuthRepository.kt */
data class SpotifyAuthResponseDto(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String? = null
)
