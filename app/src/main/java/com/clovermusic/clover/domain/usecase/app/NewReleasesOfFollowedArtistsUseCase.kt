package com.clovermusic.clover.domain.usecase.app

import android.util.Log
import com.clovermusic.clover.domain.model.Albums
import com.clovermusic.clover.domain.usecase.artist.ArtistUseCases
import com.clovermusic.clover.domain.usecase.user.UserUseCases
import com.clovermusic.clover.util.Parsers.parseReleaseDate
import kotlinx.coroutines.coroutineScope
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
    suspend operator fun invoke(limit: Int?): List<Albums> = coroutineScope {
        runCatching {
            val followedArtists = userUseCases.followedArtists()
            val artistIds = followedArtists.map { it.id }

            val latestReleases = artistsUseCase.artistAlbums(artistIds, limit)

            val data = latestReleases.sortedByDescending { res ->
                parseReleaseDate(res.releaseDate)
            }.take(6)
            Log.i(
                "LatestReleasesUseCase",
                "getLatestReleasesOfFollowedArtists: Success, total albums: $data"
            )
            data
        }.onFailure { e ->
            Log.e("LatestReleasesUseCase", "Error fetching latest releases of followed artists", e)
        }.getOrThrow()

    }
}