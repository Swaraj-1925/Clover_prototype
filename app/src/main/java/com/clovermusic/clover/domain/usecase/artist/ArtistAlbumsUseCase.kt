package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.flow.toList
import javax.inject.Inject


class ArtistAlbumsUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: Repository
) {
    /**
     * Get albums for artists. Set limit to null to get all albums.
     */

    suspend operator fun invoke(
        artistIds: List<String>,
        limit: Int?,
        forceRefresh: Boolean
    ): List<AlbumEntity> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val albums = mutableListOf<AlbumEntity>()

            artistIds.forEach { id ->
                val flow = repository.artists.getArtistAlbums(id, limit, forceRefresh)
                flow.toList().let { dataStates ->
                    dataStates.forEach { dataState ->
                        when (dataState) {
                            is DataState.NewData -> {
                                albums.clear()
                                albums.addAll(dataState.data)
                            }

                            is DataState.OldData -> albums.addAll(dataState.data)
                            is DataState.Error -> Log.e(
                                "ArtistAlbumsUseCase",
                                "Error fetching albums for artist $id: ${dataState.message}"
                            )
                        }
                    }
                }
            }
            albums
        }.onFailure { e ->
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
        }.getOrThrow()
    }
}
