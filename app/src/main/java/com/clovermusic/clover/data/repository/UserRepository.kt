package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.userResponseModels.FollowedArtistsItem
import com.clovermusic.clover.data.api.spotify.response.userResponseModels.TopArtistsItem
import com.clovermusic.clover.data.api.spotify.response.userResponseModels.TopArtistsResponse
import com.clovermusic.clover.data.api.spotify.service.UserService
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

    suspend fun getFollowedArtists(): Flow<Resource<List<FollowedArtistsItem>>> = flow {
        emit(Resource.Loading())

        val followedArtists = mutableListOf<FollowedArtistsItem>()
        val response = userService.getFollowedArtists()
        followedArtists.addAll(response.artists.items)
        var after: String? = response.artists.cursors.after

        try {
            while (after != null) {
                val res = userService.getFollowedArtists(after)
                Log.d("UserRepository", "getFollowedArtists: in loop")
                after = res.artists.cursors.after
                followedArtists.addAll(res.artists.items)
            }

            emit(Resource.Success(followedArtists.toList())) // Convert to List before emitting
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error. Please contact support for assistance."))
        } catch (e: IOException) {
            emit(Resource.Error("Network error occurred during authentication. Please try again later."))
        }
    }

    suspend fun getTopArtists(timeRange: String = "short_term"): Flow<Resource<List<TopArtistsItem>>> =
        flow {
            emit(Resource.Loading())

            val topArtists = mutableListOf<TopArtistsItem>()
            var nextUrl: String? = null
            try {
                val response: TopArtistsResponse = userService.getTopArtists(timeRange)
                Log.i(
                    "UserRepositoryImpl",
                    "getFollowedArtists : Success ${response.items[0].images}"
                )
                topArtists.addAll(response.items)
                if (response.next == null) {
//                    Log.i("UserRepositoryImpl", "getFollowedArtists1 : Success $topArtists")
                    emit(Resource.Success(topArtists.toList()))
                } else {
                    nextUrl = response.next
                    do {
                        val res = userService.getNextPage<TopArtistsResponse>(nextUrl)
                        nextUrl = res.next
                        topArtists.addAll(res.items)
                    } while (nextUrl != null)
                    emit(Resource.Success(topArtists.toList()))
                }
            } catch (e: Exception) {
                Log.e("UserRepositoryImpl", "getFollowedArtists2 Exception: ", e)
                emit(Resource.Error("Unknown error. Please contact support for assistance."))
            } catch (e: IOException) {
                Log.e("UserRepositoryImpl", "getFollowedArtists3 IOException: ${e.message}", e)
                emit(Resource.Error("Network error occurred during authentication. Please try again later."))
            }

        }.catch { e ->
            emit(Resource.Error("Error fetching top artists: ${e.localizedMessage}"))
        }
}