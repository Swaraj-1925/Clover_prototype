package com.clovermusic.clover.domain.usecase.app

import javax.inject.Inject

data class AppUseCases @Inject constructor(
    val latestReleasesUseCase: NewReleasesOfFollowedArtistsUseCase
)
