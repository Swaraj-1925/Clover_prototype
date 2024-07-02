package com.clovermusic.clover.domain.spotify.useCase

import android.util.Log
import com.clovermusic.clover.domain.spotify.models.NewReleases
import com.clovermusic.clover.domain.spotify.repository.ArtistRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ArtistUseCase @Inject constructor(
    private val artistRepository: ArtistRepository
) {

    // Function to return latest releases for followed artists
    suspend fun getLatestReleases(followedArtistIds: List<String>): List<NewReleases> =
        coroutineScope {
            try {
                val allNewReleases = followedArtistIds.map { artistId ->
                    async {
                        Log.d("ArtistUseCase", "Fetching new releases for artist $artistId")
                        val albums = artistRepository.getAlbums(artistId).items
                        // Map Item to NewReleases
                        albums.map { album ->
                            NewReleases(
                                artistsId = artistId,
                                artistsName = album.artists.firstOrNull()?.name ?: "",
                                id = album.id,
                                images = album.images.firstOrNull()?.url ?: "",
                                name = album.name,
                                release_date = album.release_date,
                                total_tracks = album.total_tracks,
                                type = album.type
                            )
                        }
                    }
                }.awaitAll().flatten()
                return@coroutineScope allNewReleases

            } catch (e: Exception) {
                Log.e("ArtistUseCase", "Failed to get new releases", e)
                return@coroutineScope emptyList()
            }
        }

}
