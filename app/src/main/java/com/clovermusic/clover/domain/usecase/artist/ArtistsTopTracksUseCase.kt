package com.clovermusic.clover.domain.usecase.artist

import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toPlaylistItems
import com.clovermusic.clover.domain.model.PlaylistItem
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistsTopTracksUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(artistId: String): Flow<Resource<List<PlaylistItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                authRepository.ensureValidAccessToken(
                    onTokenRefreshed = {
                        val res = artistRepository.getArtistTopTracks(artistId)
                        if (res.isNotEmpty()) {
                            emit(Resource.Success(toPlaylistItems(res)))
                        }
                    },
                    onError = {}
                )
            } catch (e: Exception) {

            }
        }
}