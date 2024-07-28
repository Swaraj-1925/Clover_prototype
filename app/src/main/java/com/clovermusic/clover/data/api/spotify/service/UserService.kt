package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.users.FollowedArtistsDto
import com.clovermusic.clover.data.api.spotify.response.users.TopArtistsResponseDto
import com.clovermusic.clover.data.api.spotify.response.users.UsersProfileResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
// All the function required in User repository are declared here
interface UserService {
// Give the data of followed artist
    @GET("me/following?type=artist")
    suspend fun getFollowedArtists(
        @Query("after") after: String? = null
    ): FollowedArtistsDto

// Give the top artist of the user
    @GET("me/top/artists")
    suspend fun getTopArtists(
        @Query("time_range") timeRange: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 50
    ): TopArtistsResponseDto
// User data
    @GET("me")
    suspend fun getCurrentUsersProfile(): UsersProfileResponseDto

    @GET("users/{user_id}")
    suspend fun getUsersProfile(
        @Path("user_id") userId: String
    ): UsersProfileResponseDto
// follow the playlist
    @PUT("playlists/{playlist_id}/followers")
    suspend fun followPlaylist(
        @Path("playlist_id") playlistId: String
    )
// unfollow the playlist
    @DELETE("playlists/{playlist_id}/followers")
    suspend fun unfollowPlaylist(
        @Path("playlist_id") playlistId: String
    )
// to check whether the user follows the artist or not

    @GET("me/following/contains")
    suspend fun checkIfUserFollowsArtistsOrUsers(
        @Query("type") type: String,
        @Query("ids") ids: String
    ): List<Boolean>
// check whether the user follows the playlist or not
    @GET("playlists/{playlist_id}/followers/contains")
    suspend fun checkIfCurrentUserFollowsPlaylist(
        @Path("playlist_id") playlistId: String,
    ): Boolean
}