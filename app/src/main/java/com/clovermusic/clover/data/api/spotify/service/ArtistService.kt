package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.artistResponseModels.ArtistsAlbumsResponse
import com.clovermusic.clover.data.api.spotify.response.artistResponseModels.ArtistsTopTracksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistService {

    @GET("artists/{trackId}/albums")
    suspend fun getArtistAlbums(
        @Path("trackId") artistId: String,
        @Query("include_groups") includeGroups: String = "album,single",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int,
    ): ArtistsAlbumsResponse

    @GET("artists/{artistId}/top-tracks")
    suspend fun getArtistTopTracks(
        @Path("artistId") artistId: String,
    ): ArtistsTopTracksResponse
}