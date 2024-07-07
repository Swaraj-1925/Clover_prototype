package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toTrack
import com.clovermusic.clover.domain.model.Track
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ArtistsTopTracksUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(artistId: String): Flow<Resource<List<Track>>> =
        flow {
            emit(Resource.Loading())
            try {
                authRepository.ensureValidAccessToken(
                    onTokenRefreshed = {
                        val res = artistRepository.getArtistTopTracks(artistId)
                        if (res.isNotEmpty()) {
                            emit(Resource.Success(res.toTrack()))
                        }
                    },
                    onError = { error ->
                        emit(Resource.Error("Failed to refresh token: $error"))
                    }
                )
            } catch (e: IOException) {
                Log.e("ArtistTopTracksUseCase", "Network error: ${e.message}")
                emit(Resource.Error("Network error occurred. Please try again later."))
            } catch (e: Exception) {
                Log.e("ArtistTopTracksUseCase", "Error getting data: ${e.message}")
                emit(Resource.Error("An error occurred while fetching playlists"))
            }
        }
}