package com.clovermusic.clover.data.spotify.api.service

import com.clovermusic.clover.data.spotify.api.dto.search.SearchResponseDto
import com.clovermusic.clover.data.spotify.api.dto.users.FollowedArtistsDto
import com.clovermusic.clover.data.spotify.api.dto.users.TopArtistsResponseDto
import com.clovermusic.clover.data.spotify.api.dto.users.UsersProfileResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("me/following?type=artist")
    suspend fun getFollowedArtists(
        @Query("after") after: String? = null
    ): FollowedArtistsDto


    @GET("me/top/artists")
    suspend fun getTopArtists(
        @Query("time_range") timeRange: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 50
    ): TopArtistsResponseDto

    @GET("me")
    suspend fun getCurrentUsersProfile(): UsersProfileResponseDto

    @GET("users/{user_id}")
    suspend fun getUsersProfile(
        @Path("user_id") userId: String
    ): UsersProfileResponseDto

    @PUT("playlists/{playlist_id}/followers")
    suspend fun followPlaylist(
        @Path("playlist_id") playlistId: String
    )

    @DELETE("playlists/{playlist_id}/followers")
    suspend fun unfollowPlaylist(
        @Path("playlist_id") playlistId: String
    )


    @GET("me/following/contains")
    suspend fun checkIfUserFollowsArtistsOrUsers(
        @Query("type") type: String,
        @Query("ids") ids: String
    ): List<Boolean>

    @GET("playlists/{playlist_id}/followers/contains")
    suspend fun checkIfCurrentUserFollowsPlaylist(
        @Path("playlist_id") playlistId: String,
    ): Boolean


    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("limit") limit: Int = 20
    ): SearchResponseDto

}