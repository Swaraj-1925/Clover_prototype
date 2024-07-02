package com.clovermusic.clover.data.spotify.network

import com.clovermusic.clover.data.spotify.models.user.followedArtists.FollowedArtistsResponse
import com.clovermusic.clover.data.spotify.models.user.topItems.TopArtistsResponse
import retrofit2.http.GET

interface UserApiService {

    @GET("me/following?type=artist&limit=50")
    suspend fun getFollowedArtists(): FollowedArtistsResponse

    @GET("me/top/artists?limit=50")
    fun getTopArtists(): TopArtistsResponse


}