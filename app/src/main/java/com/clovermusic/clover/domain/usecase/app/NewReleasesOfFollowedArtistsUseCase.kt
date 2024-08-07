package com.clovermusic.clover.domain.usecase.app

import android.util.Log
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.relations.ArtistWithAlbums
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.util.DataState
import com.clovermusic.clover.util.Parsers
import com.clovermusic.clover.util.customErrorHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

/**
 * Coroutine scope function to fetch latest releases of followed artists.
 * It creates a new coroutine scope and doesn't complete until all launched children coroutines complete
 * If any of the child coroutines throw an exception, coroutineScope will cancel all other
 * children and re-throw the exception
 */
class NewReleasesOfFollowedArtistsUseCase @Inject constructor(
    private val userUseCases: UserUseCases,
    private val artistsUseCase: ArtistUseCases
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    suspend operator fun invoke(
        forceRefresh: Boolean,
        limit: Int? = null // This limit is for albums per artist
    ): Flow<DataState<List<ArtistWithAlbums>>> = flow {
        emit(DataState.Loading)

        try {
            // Fetch followed artists
            val artistIds = userUseCases.followedArtists(forceRefresh)
                .filterIsInstance<DataState.NewData<List<ArtistsEntity>>>()
                .map { it.data.map { artist -> artist.artistId } }
                .first()

            // Fetch artist albums
            artistsUseCase.artistAlbums(
                artistIds = artistIds,
                limit = limit,
                forceRefresh = forceRefresh
            ).collect { state ->
                when (state) {
                    is DataState.Loading -> emit(state)
                    is DataState.Error -> emit(state)
                    is DataState.OldData -> {
                        val latestReleases = processAlbums(state.data)
                        emit(DataState.OldData(latestReleases))
                    }

                    is DataState.NewData -> {
                        val latestReleases = processAlbums(state.data)
                        emit(DataState.NewData(latestReleases))
                    }
                }
            }

        } catch (e: Exception) {
            Log.e(
                "NewReleasesOfFollowedArtistsUseCase",
                "Error fetching new releases of followed artists",
                e
            )
            emit(DataState.Error(customErrorHandling(e)))
        }
    }.flowOn(Dispatchers.Default)

    private fun processAlbums(albums: List<ArtistWithAlbums>): List<ArtistWithAlbums> {
        return albums.map { artistWithAlbums ->
            val latestAlbum = artistWithAlbums.albums
                .maxByOrNull { Parsers.parseReleaseDate(it.releaseDate) }
            artistWithAlbums.copy(albums = listOfNotNull(latestAlbum))
        }.sortedByDescending {
            it.albums.firstOrNull()?.let { album -> Parsers.parseReleaseDate(album.releaseDate) }
        }.take(6)
    }
}