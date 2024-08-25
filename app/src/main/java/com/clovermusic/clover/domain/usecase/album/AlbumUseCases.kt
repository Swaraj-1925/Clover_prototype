package com.clovermusic.clover.domain.usecase.album

import javax.inject.Inject

data class AlbumUseCases @Inject constructor(
    val getAlbums: GetAlbumUseCase
)
