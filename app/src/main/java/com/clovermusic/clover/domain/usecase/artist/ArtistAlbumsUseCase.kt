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

            val refreshArtistId = mutableSetOf<String>()
            val artistAlbums = mutableListOf<ArtistWithAlbums>()

            val storedAlbums = artistIds.map { artistId ->
                val albums = repository.artists.getStoredArtistAlbums(artistId)
                if (albums.albums.isEmpty()) {
                    refreshArtistId.add(artistId)
                } else {
                    artistAlbums.add(albums)
                }
            }
            refreshArtistId.forEach { artistId ->
                val albums = repository.artists.getAndStoreArtistAlbumsFromApi(artistId, limit)
                artistAlbums.add(albums)
            }

            if (artistAlbums.isNotEmpty()) {
                Log.d("ArtistAlbumsUseCase", "Fetching fresh artist albums")
                emit(DataState.OldData(artistAlbums))
            }

            val freshAlbums = artistIds.map { artistId ->
                repository.artists.getAndStoreArtistAlbumsFromApi(artistId, limit)
            }
            val dataChanged = freshAlbums != artistAlbums

            val needsRefresh = forceRefresh || dataChanged || storedAlbums.isEmpty()


            if (needsRefresh) {
                emit(DataState.NewData(freshAlbums))
            }

        } catch (e: Exception) {
            Log.e("ArtistAlbumsUseCase", "Error fetching artist albums", e)
            coroutineScope {
                emit(DataState.Error(customErrorHandling(e)))
                val storedAlbums = artistIds.map { artistId ->
                    async { repository.artists.getStoredArtistAlbums(artistId) }
                }.awaitAll()

                if (storedAlbums.isNotEmpty()) {
                    emit(DataState.OldData(storedAlbums))
                } else {
                    emit(DataState.Error(customErrorHandling(e)))
                }
            }
        }
    }.flowOn(Dispatchers.Default)
}
