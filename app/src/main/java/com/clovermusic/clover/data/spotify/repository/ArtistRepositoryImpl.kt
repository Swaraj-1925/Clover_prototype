package com.clovermusic.clover.data.spotify.repository

import com.clovermusic.clover.data.spotify.models.artist.artistAlbums.ArtistAlbums
import com.clovermusic.clover.data.spotify.network.ArtistApiService
import com.clovermusic.clover.domain.spotify.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val apiService: ArtistApiService
) : ArtistRepository {

    override suspend fun getAlbums(artistId: String): ArtistAlbums {
        return apiService.getNewReleases(artistId)
    }
}