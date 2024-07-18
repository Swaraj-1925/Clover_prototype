package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.artists.ArtistsAlbumsResponseDto
import com.clovermusic.clover.data.api.spotify.response.artists.ArtistsTopTracksResponseDto
import com.clovermusic.clover.data.api.spotify.response.common.TrackArtistResponseDto
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
    ): ArtistsAlbumsResponseDto

    @GET("artists/{artistId}/top-tracks")
    suspend fun getArtistTopTracks(
        @Path("artistId") artistId: String,
    ): ArtistsTopTracksResponseDto

    @GET("artists/{id}/related-artists")
    suspend fun getArtistRelatedArtists(
        @Path("id") id: String
    ): TrackArtistResponseDto
}