package com.clovermusic.clover.domain.spotify.repository

import com.clovermusic.clover.data.spotify.models.artist.artistAlbums.ArtistAlbums

interface ArtistRepository {

    suspend fun getAlbums(artistId: String): ArtistAlbums
}