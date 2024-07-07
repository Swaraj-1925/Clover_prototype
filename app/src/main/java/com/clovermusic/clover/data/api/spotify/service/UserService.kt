package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.users.FollowedArtistsDto
import com.clovermusic.clover.data.api.spotify.response.users.TopArtistsResponseDto
import retrofit2.http.GET
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
}