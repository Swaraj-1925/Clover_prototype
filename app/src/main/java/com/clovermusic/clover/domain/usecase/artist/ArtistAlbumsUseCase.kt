package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toArtistAlbums
import com.clovermusic.clover.domain.model.Albums
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ArtistAlbumsUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val authRepository: SpotifyAuthRepository
) {

    suspend operator fun invoke(artistId: List<String>): Flow<Resource<List<Albums>>> =
        flow {
            emit(Resource.Loading())
            try {
                authRepository.ensureValidAccessToken(
                    onTokenRefreshed = {
                        artistId.forEach { artistId ->
                            val response = artistRepository.getArtistAlbums(artistId)
                            if (response.isNotEmpty()) {
                                emit(Resource.Success(toArtistAlbums(response)))
                            } else {
                                emit(Resource.Error("No artist albums found"))
                            }
                        }
                    },
                    onError = { error ->
                        emit(Resource.Error("Failed to refresh token: $error"))
                    }
                )
            } catch (e: IOException) {
                Log.e("ArtistAlbumsUseCase", "Network error: ${e.message}")
                emit(Resource.Error("Network error occurred. Please try again later."))
            } catch (e: Exception) {
                Log.e("ArtistAlbumsUseCase", "Error getting data: ${e.message}")
                emit(Resource.Error("An error occurred while fetching playlists"))
            }
        }

}