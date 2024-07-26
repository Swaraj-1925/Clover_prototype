package com.clovermusic.clover.data.spotify.api.repository

import android.util.Log
import com.clovermusic.clover.data.spotify.api.response.common.TrackArtistResponseDto
import com.clovermusic.clover.data.spotify.api.response.users.UsersProfileResponseDto
import com.clovermusic.clover.data.spotify.api.service.UserService
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
    suspend fun getFollowedArtists(): List<TrackArtistResponseDto> =
        withContext(Dispatchers.IO) {
            try {
                val followedArtists =
                    mutableListOf<TrackArtistResponseDto>()
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
                val topArtists =
                    mutableListOf<TrackArtistResponseDto>()
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

    //    Function to get Current (Logged in) User Profile
    suspend fun getCurrentUsersProfile(): UsersProfileResponseDto =
        withContext(Dispatchers.IO) {
            try {
                val response = userService.getCurrentUsersProfile()
                response
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException(
                    "UserRepository",
                    "getCurrentUsersProfile",
                    e
                )
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "UserRepository",
                    "getCurrentUsersProfile",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException(
                    "UserRepository",
                    "getCurrentUsersProfile",
                    e
                )
            }
        }

    //    Function to get any other user profile
    suspend fun getUsersProfile(userId: String): UsersProfileResponseDto =
        withContext(Dispatchers.IO) {
            try {
                val response = userService.getUsersProfile(userId)
                response
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException("UserRepository", "getUsersProfile", e)
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "UserRepository",
                    "getUsersProfile",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException("UserRepository", "getUsersProfile", e)
            }
        }

    //    Function to follow a playlist
    suspend fun followPlaylist(playlistId: String): Unit = withContext(Dispatchers.IO) {
        try {
            userService.followPlaylist(playlistId)
            Log.d("UserRepository", "Successfully followed playlist with ID: $playlistId")
        } catch (e: HttpException) {
            SpotifyApiException.handleApiException("UserRepository", "followPlaylist", e)
        } catch (e: Exception) {
            throw CustomException.UnknownException(
                "UserRepository",
                "followPlaylist",
                "Unknown error",
                e
            )
        } catch (e: IOException) {
            throw CustomException.NetworkException("UserRepository", "followPlaylist", e)
        }
    }

    //    Function to unfollow a playlist
    suspend fun unfollowPlaylist(playlistId: String): Unit = withContext(Dispatchers.IO) {
        try {
            userService.unfollowPlaylist(playlistId)
            Log.d("UserRepository", "Successfully unfollowed playlist with ID: $playlistId")
        } catch (e: HttpException) {
            SpotifyApiException.handleApiException("UserRepository", "unfollowPlaylist", e)
        } catch (e: Exception) {
            throw CustomException.UnknownException(
                "UserRepository",
                "unfollowPlaylist",
                "Unknown error",
                e
            )
        } catch (e: IOException) {
            throw CustomException.NetworkException("UserRepository", "unfollowPlaylist", e)
        }
    }

    //    Function to check if user follows artists or users
    suspend fun checkIfUserFollowsArtistsOrUsers(type: String, ids: List<String>): List<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val idsParam = ids.joinToString(",")
                val response = userService.checkIfUserFollowsArtistsOrUsers(type, idsParam)
                Log.d("UserRepository", "Checked if user follows artists or users: $response")
                response
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException(
                    "UserRepository",
                    "checkIfUserFollowsArtistsOrUsers",
                    e
                )
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "UserRepository",
                    "checkIfUserFollowsArtistsOrUsers",
                    "Unknown error",
                    e
                )
            } catch (e: IOException) {
                throw CustomException.NetworkException(
                    "UserRepository",
                    "checkIfUserFollowsArtistsOrUsers",
                    e
                )
            }
        }

    //    Function to check if current user follows playlist
    suspend fun checkIfCurrentUserFollowsPlaylist(
        playlistId: String,
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = userService.checkIfCurrentUserFollowsPlaylist(playlistId)
            Log.d("UserRepository", "Checked if current user follows playlist: $response")
            response
        } catch (e: HttpException) {
            SpotifyApiException.handleApiException(
                "UserRepository",
                "checkIfCurrentUserFollowsPlaylist",
                e
            )
        } catch (e: Exception) {
            throw CustomException.UnknownException(
                "UserRepository",
                "checkIfCurrentUserFollowsPlaylist",
                "Unknown error",
                e
            )
        } catch (e: IOException) {
            throw CustomException.NetworkException(
                "UserRepository",
                "checkIfCurrentUserFollowsPlaylist",
                e
            )
        }
    }
}