package com.clovermusic.clover.domain.usecase.artist

import javax.inject.Inject


data class ArtistUseCases @Inject constructor(
    val artistAlbums: ArtistAlbumsUseCase,
    val artistTopTrack: ArtistsTopTracksUseCase,
    val getArtistRelatedArtists: GetArtistRelatedArtistsUseCase,
    val getArtist: ArtistInfoUseCase
)
