package com.clovermusic.clover.domain.usecase.artist


data class ArtistUseCases(
    val artistAlbums: ArtistAlbumsUseCase,
    val artistTopTrack: ArtistsTopTracksUseCase,
    val getArtistRelatedArtists: GetArtistRelatedArtistsUseCase
)
