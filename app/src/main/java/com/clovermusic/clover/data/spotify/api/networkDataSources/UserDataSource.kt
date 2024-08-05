package com.clovermusic.clover.data.spotify.api.networkDataSources

import android.util.Log
import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.users.UsersProfileResponseDto
import com.clovermusic.clover.data.spotify.api.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService,
) {
    suspend fun fetchFollowedArtists(): List<TrackArtistResponseDto> = withContext(Dispatchers.IO) {
        val followedArtists = mutableListOf<TrackArtistResponseDto>()
        var after: String? = null
        do {
            val response = userService.getFollowedArtists(after)
            followedArtists.addAll(response.artists.items)
            after = response.artists.cursors?.after
            Log.i(
                "SpotifyRemoteDataSource",
                "fetchFollowedArtists: fetched batch, size: ${response.artists.items.size}"
            )
        } while (after != null)
        Log.i(
            "SpotifyRemoteDataSource",
            "fetchFollowedArtists: total artists fetched: ${followedArtists.size}"
        )
        followedArtists
    }

    suspend fun fetchTopArtists(timeRange: String): List<TrackArtistResponseDto> =
        withContext(Dispatchers.IO) {

            val topArtists =
                mutableListOf<TrackArtistResponseDto>()
            var total: Int
            var offset = 0
            do {
                val response = userService.getTopArtists(timeRange = timeRange, offset = offset)
                topArtists.addAll(response.items)
                total = response.total
                offset += response.limit
                Log.i(
                    "UserRepository",
                    "getTopArtists: fetched batch, size: ${response.total} and offset: $offset"
                )
            } while (offset < total)

            Log.i(
                "UserRepository",
                "getTopArtists: total items fetched: ${topArtists.size}"
            )
            topArtists
        }

    suspend fun fetchCurrentUsersProfile(): UsersProfileResponseDto =
        withContext(Dispatchers.IO) {
            val response = userService.getCurrentUsersProfile()
            response
        }

    suspend fun fetchUsersProfile(userId: String): UsersProfileResponseDto =
        withContext(Dispatchers.IO) {
            val response = userService.getUsersProfile(userId)
            response
        }

}