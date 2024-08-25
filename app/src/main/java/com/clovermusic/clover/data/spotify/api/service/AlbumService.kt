package com.clovermusic.clover.data.spotify.api.service

import com.clovermusic.clover.data.spotify.api.dto.albums.SpecificAlbumResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface AlbumService {
    @GET("albums/{id}")
    suspend fun getAlbum(
        @Path("artistId") artistId: String,
    ): SpecificAlbumResponseDto

    @GET
    suspend fun getAlbumTrack(@Url url: String): SpecificAlbumResponseDto
}