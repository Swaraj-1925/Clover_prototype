package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.artistResponseModels.ArtistsAlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ArtistService {

    @GET("artists/{trackId}/albums")
    suspend fun getArtistAlbums(
        @Path("trackId") artistId: String,
        @Query("include_groups") includeGroups: String = "album,single",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int,
    ): ArtistsAlbumsResponse


    @GET
    suspend fun <T> getNextPage(@Url url: String): T
}