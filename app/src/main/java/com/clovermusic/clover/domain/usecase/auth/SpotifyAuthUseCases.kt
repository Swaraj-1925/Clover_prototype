package com.clovermusic.clover.domain.usecase.auth

import javax.inject.Inject

data class SpotifyAuthUseCases @Inject constructor(
    val createIntent: CreateSpotifyAuthIntentUseCase,
    val handleAuthResponse: HandleSpotifyAuthIntentResponseUseCase
)
