package com.clovermusic.clover.data.spotify.api.response.auth

data class SpotifyAuthResponseDto(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String? = null
)
