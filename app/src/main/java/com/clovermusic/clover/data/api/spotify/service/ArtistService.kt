package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.artists.ArtistRelatedArtistsResponseDto
import com.clovermusic.clover.data.api.spotify.response.artists.ArtistsAlbumsResponseDto
import com.clovermusic.clover.data.api.spotify.response.artists.ArtistsTopTracksResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
// All the function used in Artist repository are declared here
interface ArtistService {
// All the album of the artist are return
    @GET("artists/{trackId}/albums")
    suspend fun getArtistAlbums(
        @Path("trackId") artistId: String,
        @Query("include_groups") includeGroups: String = "album,single",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int,
    ): ArtistsAlbumsResponseDto
// Top tracks of the artist are return
    @GET("artists/{artistId}/top-tracks")
    suspend fun getArtistTopTracks(
        @Path("artistId") artistId: String,
    ): ArtistsTopTracksResponseDto
// To return artist related to the current artist
    @GET("artists/{id}/related-artists")
    suspend fun getArtistRelatedArtists(
        @Path("id") id: String
    ): ArtistRelatedArtistsResponseDto
}