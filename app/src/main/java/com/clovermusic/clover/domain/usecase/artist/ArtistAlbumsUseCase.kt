package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.spotify.api.repository.ArtistRepository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toArtistAlbums
import com.clovermusic.clover.domain.model.Albums
import javax.inject.Inject

class ArtistAlbumsUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    /**
     *  get albums for artists set limit to null to get all albums
     */
    suspend operator fun invoke(artistIds: List<String>, limit: Int?): List<Albums> {
        return runCatching {
            val albums = mutableListOf<Albums>()
            authRepository.ensureValidAccessToken()
            artistIds.map { artistId ->
                val res = artistRepository.getArtistAlbums(artistId = artistId, limit)
                albums.addAll(res.toArtistAlbums())
            }
            Log.i("ArtistAlbumsUseCase", "getArtistAlbums: Success, total albums: $albums")
            albums
        }.onFailure { e ->
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
        }.getOrThrow()
    }
}