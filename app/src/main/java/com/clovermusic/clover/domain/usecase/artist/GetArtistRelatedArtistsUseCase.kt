package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.Util.toTrackArtists
import com.clovermusic.clover.domain.model.common.TrackArtists
import javax.inject.Inject

class GetArtistRelatedArtistsUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(id: String): List<TrackArtists> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val res = repository.getArtistRelatedArtists(id)
            res.toTrackArtists()
        }.onFailure { e ->
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
        }.getOrThrow()
    }
}