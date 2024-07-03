package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.ArtistResponseItem
import com.clovermusic.clover.data.api.spotify.service.UserService
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {
    /**
     * Function will 1st make a request get 50 artists from the api and then if there is more than 50
     * it will make request till next != null and return all followed artists
     */
    suspend fun getFollowedArtists(): Flow<Resource<List<ArtistResponseItem>>> =
        flow {
            emit(Resource.Loading())
            val followedArtists = mutableListOf<ArtistResponseItem>()
            try {
                var response = userService.getFollowedArtists()
                followedArtists.addAll(response.artists.items)
                while (response.artists.next != null) {
                    response = userService.getNextPage(response.artists.next)
                    followedArtists.addAll(response.artists.items)
                }
                Log.i("UserRepository", "getFollowedArtists : Success")
                emit(Resource.Success(followedArtists))
            } catch (e: Exception) {
                Log.e("UserRepository", "getFollowedArtists Exception: ", e)
                emit(Resource.Error("Unknown error. Please contact support for assistance."))
            } catch (e: IOException) {
                Log.e("UserRepository", "getFollowedArtists IOException: ${e.message}", e)
                emit(Resource.Error("Network error occurred during authentication. Please try again later."))
            }

        }

    suspend fun getTopArtists(timeRange: String = "short_term"): Flow<Resource<List<ArtistResponseItem>>> =
        flow {
            emit(Resource.Loading())

            val followedArtists = mutableListOf<ArtistResponseItem>()
            try {
                var response = userService.getTopArtists(timeRange)
                followedArtists.addAll(response.items)
                while (response.next != null) {
                    response = userService.getNextPage(response.next)
                    followedArtists.addAll(response.items)
                }
                Log.i("UserRepositoryImpl", "getFollowedArtists : Success $followedArtists")
                emit(Resource.Success(followedArtists))
            } catch (e: Exception) {
                Log.e("UserRepositoryImpl", "getFollowedArtists Exception: ", e)
                emit(Resource.Error("Unknown error. Please contact support for assistance."))
            } catch (e: IOException) {
                Log.e("UserRepositoryImpl", "buildSpotifyAuthRequest IOException: ${e.message}", e)
                emit(Resource.Error("Network error occurred during authentication. Please try again later."))
            }

        }
}