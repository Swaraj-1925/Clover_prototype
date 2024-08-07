package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.local.entity.relations.ArtistWithAlbums
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ArtistAlbumsUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
    /**
     * Get albums for artists. Set limit to null to get all albums.
     */
    suspend operator fun invoke(
        artistIds: List<String>,
        limit: Int?,
        forceRefresh: Boolean
    ): Flow<DataState<List<ArtistWithAlbums>>> = flow {
        try {
            networkDataAction.authData.ensureValidAccessToken()

            coroutineScope {
                // Fetch stored albums in parallel
                val storedAlbums = artistIds.map { artistId ->
                    async {
                        repository.artists.getStoredArtistAlbums(artistId)
                    }
                }.awaitAll().filter { it.albums.isNotEmpty() }

                val needsRefresh = storedAlbums.size != artistIds.size || forceRefresh

                if (needsRefresh) {
                    Log.d("ArtistAlbumsUseCase", "Fetching fresh albums")
                    // Fetch fresh albums in parallel
                    val freshAlbums = artistIds.map { artistId ->
                        async {
                            repository.artists.getAndStoreArtistAlbumsFromApi(artistId, limit)
                        }
                    }.awaitAll()

                    emit(DataState.NewData(freshAlbums))
                } else {
                    Log.d("ArtistAlbumsUseCase", "Using cached albums")
                    emit(DataState.OldData(storedAlbums))

                    // Fetch fresh albums in parallel to update the cache
                    val freshAlbums = artistIds.map { artistId ->
                        async {
                            repository.artists.getAndStoreArtistAlbumsFromApi(artistId, limit)
                        }
                    }.awaitAll()

                    val isDataChanged = freshAlbums != storedAlbums
                    if (isDataChanged) {
                        emit(DataState.NewData(freshAlbums))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ArtistAlbumsUseCase", "Error fetching artist albums", e)
            coroutineScope {
                val storedAlbums = artistIds.map { artistId ->
                    async {
                        repository.artists.getStoredArtistAlbums(artistId)
                    }
                }.awaitAll()
                if (storedAlbums.isNotEmpty()) {
                    emit(DataState.OldData(storedAlbums))
                } else {
                    emit(DataState.Error(customErrorHandling(e)))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}
