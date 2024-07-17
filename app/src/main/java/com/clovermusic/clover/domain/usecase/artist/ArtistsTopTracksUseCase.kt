package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toTrack
import com.clovermusic.clover.domain.model.Track
import javax.inject.Inject

class ArtistsTopTracksUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    //    Get top tracks of Artists
    suspend operator fun invoke(artistId: String): List<Track> {
        return runCatching {
            val track = mutableListOf<Track>()
            authRepository.ensureValidAccessToken()
            val res = artistRepository.getArtistTopTracks(artistId)
            track.addAll(res.toTrack())
            track
        }.onFailure { e ->
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
        }.getOrThrow()
    }
}