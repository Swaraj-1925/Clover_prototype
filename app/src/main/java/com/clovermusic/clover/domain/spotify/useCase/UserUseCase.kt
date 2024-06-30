package com.clovermusic.clover.domain.spotify.useCase

import android.util.Log
import com.clovermusic.clover.domain.spotify.models.NewReleases
import com.clovermusic.clover.domain.spotify.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val artistUseCase: ArtistUseCase
) {
    // Function to extract and return followed artists IDs
    private suspend fun getFollowedArtistsId() = coroutineScope {
        try {
            val followedArtists = async { userRepository.followedArtists() }
            val followedArtistIds = followedArtists.await()
                .artists
                .items
                .map { it.id }
            Log.d("UserUseCase", "Fetched followed artists")
            return@coroutineScope followedArtistIds
        } catch (e: Exception) {
            Log.e("UserUseCase", "Failed to fetch followed artists", e)
            return@coroutineScope emptyList()
        }
    }

    // Function to return latest releases for followed artists
    suspend fun getLatestReleases(): List<NewReleases> = coroutineScope {
        try {
            val followedArtistIds = getFollowedArtistsId()
            if (followedArtistIds.isEmpty()) {
                Log.e("UserUseCase", "Failed to get followed artist IDs")
                return@coroutineScope emptyList()
            }
            // Use ArtistUseCase to get latest releases
            val latestReleases = artistUseCase.getLatestReleases(followedArtistIds)
            val top6NewReleases = latestReleases
                .sortedByDescending { it.release_date }
                .take(6)
            return@coroutineScope top6NewReleases

        } catch (e: Exception) {
            Log.e("UserUseCase", "Failed to get new releases", e)
            return@coroutineScope emptyList()
        }
    }

}
