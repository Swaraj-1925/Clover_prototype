package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.userResponseModels.FollowedArtistsItem
import com.clovermusic.clover.data.api.spotify.response.userResponseModels.TopArtistsItem
import com.clovermusic.clover.data.api.spotify.response.userResponseModels.TopArtistsResponse
import com.clovermusic.clover.data.api.spotify.service.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    private val spotifyAuthRepository: SpotifyAuthRepository
) {
    /**
     * Function will 1st make a request get 50 artists from the api and then if there is more than 50
     * it will make request till next != null and return all followed artists
     */
    suspend fun getFollowedArtists(): List<FollowedArtistsItem> {
        return try {
            val followedArtists = mutableListOf<FollowedArtistsItem>()
            var after: String? = null
            do {
                val response = userService.getFollowedArtists(after)
                followedArtists.addAll(response.artists.items)
                after = response.artists.cursors.after
                Log.d(
                    "UserRepository",
                    "getFollowedArtists: fetched batch, size: ${response.artists.items.size}"
                )
            } while (after != null)

            Log.d(
                "UserRepository",
                "getFollowedArtists: total artists fetched: ${followedArtists.size}"
            )
            followedArtists
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching followed artists: ${e.message}", e)
            throw e // Rethrow the exception to be handled in the use case
        }
    }

    suspend fun getTopArtists(timeRange: String = "short_term"): List<TopArtistsItem> {
        return try {
            val topArtists = mutableListOf<TopArtistsItem>()
            var nextUrl: String? = null

            val initialResponse = userService.getTopArtists(timeRange)
            topArtists.addAll(initialResponse.items)
            nextUrl = initialResponse.next

            while (nextUrl != null) {
                val response = userService.getNextPage<TopArtistsResponse>(nextUrl)
                topArtists.addAll(response.items)
                nextUrl = response.next
                Log.d(
                    "UserRepository",
                    "getTopArtists: fetched batch, size: ${response.items.size}"
                )
            }

            Log.d("UserRepository", "getTopArtists: total artists fetched: ${topArtists.size}")
            topArtists
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching top artists: ${e.message}", e)
            throw e // Rethrow the exception to be handled in the use case
        }
    }
}