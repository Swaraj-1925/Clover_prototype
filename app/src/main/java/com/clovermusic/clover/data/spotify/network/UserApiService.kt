package com.clovermusic.clover.data.spotify.network

import com.clovermusic.clover.data.spotify.models.artist.artistAlbums.ArtistAlbums
import com.clovermusic.clover.data.spotify.models.user.followedArtists.FollowedArtistsResponse
import com.clovermusic.clover.data.spotify.models.user.topItems.TopArtistsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    @GET("me/following?type=artist&limit=50")
    suspend fun getfollowedArtists(): FollowedArtistsResponse

    @GET("/me/top/artists?limit=50")
    fun getTopArtists(): TopArtistsResponse


    @GET("artists/{id}/albums")
    suspend fun getNewReleases(
        @Path("id") artistId: String,
        @Query("include_groups") includeGroups: String = "album,single",
        @Query("limit") limit: Int = 50
    ): ArtistAlbums

}