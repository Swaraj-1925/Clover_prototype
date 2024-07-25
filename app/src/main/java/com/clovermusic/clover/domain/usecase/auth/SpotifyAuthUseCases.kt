package com.clovermusic.clover.domain.usecase.auth

data class SpotifyAuthUseCases(
    val createIntent: CreateSpotifyAuthIntentUseCase,
    val handleAuthResponse: HandleSpotifyAuthIntentResponseUseCase
)
