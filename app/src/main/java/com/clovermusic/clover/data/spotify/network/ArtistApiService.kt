package com.clovermusic.clover.data.spotify.network

import com.clovermusic.clover.data.spotify.models.artist.artistAlbums.ArtistAlbums
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistApiService {

    @GET("artists/{id}/albums")
    suspend fun getNewReleases(
        @Path("id") artistId: String,
        @Query("include_groups") includeGroups: String = "album,single",
        @Query("limit") limit: Int = 50
    ): ArtistAlbums
}