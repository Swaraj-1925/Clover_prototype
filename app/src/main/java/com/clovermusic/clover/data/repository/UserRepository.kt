package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto
import com.clovermusic.clover.data.api.spotify.service.UserService
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.SpotifyApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    private val spotifyAuthRepository: SpotifyAuthRepository
) {
    /**
     * Function will 1st make a request get 50 artists from the api and then if there is more than 50
     * it will make request till next != null and return all followed artists
     */
    suspend fun getFollowedArtists(): List<TrackArtistResponseDto> = withContext(Dispatchers.IO) {
        try {
            val followedArtists = mutableListOf<TrackArtistResponseDto>()
            var after: String? = null
            do {
                val response = userService.getFollowedArtists(after)
                followedArtists.addAll(response.artists.items)
                after = response.artists.cursors?.after
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
        } catch (e: IOException) {
            throw CustomException.NetworkException("UserRepository", "getFollowedArtists", e)
        } catch (e: Exception) {
            throw CustomException.UnknownException(
                "UserRepository",
                "getFollowedArtists",
                "Unknown error",
                e
            )
        } catch (e: HttpException) {
            SpotifyApiException.handleApiException("UserRepository", "getFollowedArtists", e)
        }

    }

    /**
     * Function to get all Artist user Listen to most
     */
    suspend fun getTopArtists(timeRange: String): List<TrackArtistResponseDto> =
        withContext(Dispatchers.IO) {
            try {
                val topArtists = mutableListOf<TrackArtistResponseDto>()
                var total: Int
                var offset = 0
                do {
                    val response = userService.getTopArtists(timeRange = timeRange, offset = offset)
                    topArtists.addAll(response.items)
                    total = response.total
                    offset += response.limit
                    Log.d(
                        "UserRepository",
                        "getTopArtists: fetched batch, size: ${response.total} and offset: $offset"
                    )
                } while (offset < total)

                Log.d(
                    "UserRepository",
                    "getTopArtists: total items fetched: ${topArtists.size}"
                )
                topArtists
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException("UserRepository", "getTopArtists", e)
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "UserRepository",
                    "getTopArtists",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException("UserRepository", "getTopArtists", e)
            }
        }
}