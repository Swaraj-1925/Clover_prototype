package com.clovermusic.clover.data.spotify.api.networkDataAction

import android.util.Log
import com.clovermusic.clover.data.spotify.api.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataAction @Inject constructor(
    private val userService: UserService,
) {
    suspend fun checkIfCurrentUserFollowsPlaylist(
        playlistId: String,
    ): Boolean = withContext(Dispatchers.IO) {
        val response = userService.checkIfCurrentUserFollowsPlaylist(playlistId)
        Log.d("UserRepository", "Checked if current user follows playlist: $response")
        response
    }

    suspend fun checkIfUserFollowsArtistsOrUsers(type: String, ids: List<String>): List<Boolean> =
        withContext(Dispatchers.IO) {
            val idsParam = ids.joinToString(",")
            val response = userService.checkIfUserFollowsArtistsOrUsers(type, idsParam)
            Log.d("UserRepository", "Checked if user follows artists or users: $response")
            response
        }

    suspend fun unfollowPlaylist(playlistId: String): Unit = withContext(Dispatchers.IO) {
        userService.unfollowPlaylist(playlistId)
        Log.d("UserRepository", "Successfully unfollowed playlist with ID: $playlistId")
    }

    suspend fun followPlaylist(playlistId: String): Unit = withContext(Dispatchers.IO) {
        userService.followPlaylist(playlistId)
        Log.d("UserRepository", "Successfully followed playlist with ID: $playlistId")
    }
}