package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.spotify.api.repository.UserRepository
import com.clovermusic.clover.domain.mapper.Util.toTrackArtists
import com.clovermusic.clover.domain.model.common.TrackArtists
import javax.inject.Inject

class TopArtistUseCase @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: SpotifyAuthRepository
) {
    //    Get list of artist which user listen to most in short time(4 Weeks) period
    suspend operator fun invoke(timeRange: String = "short_term"): List<TrackArtists> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.getTopArtists(timeRange).toTrackArtists()
        }.onFailure { e ->
            Log.e("TopArtistUseCase", "Error fetching top artists", e)
        }.getOrThrow()
    }
}