package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.userResponseModels.FollowedArtistsResponse
import com.clovermusic.clover.data.api.spotify.response.userResponseModels.TopArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface UserService {

    @GET("me/following?type=artist&limit=50")
    suspend fun getFollowedArtists(@Query("after") after: String? = null): FollowedArtistsResponse

    @GET("me/top/artists?limit=50")
    suspend fun getTopArtists(@Query("time_range") timeRange: String): TopArtistsResponse

    @GET
    suspend fun <T> getNextPage(@Url url: String): T
}