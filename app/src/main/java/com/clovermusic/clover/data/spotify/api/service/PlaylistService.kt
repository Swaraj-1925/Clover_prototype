package com.clovermusic.clover.data.spotify.api.service

import com.clovermusic.clover.data.spotify.api.response.common.ImageResponseDto
import com.clovermusic.clover.data.spotify.api.response.playlists.CreatePlaylistRequest
import com.clovermusic.clover.data.spotify.api.response.playlists.CurrentUsersPlaylistResponseDto
import com.clovermusic.clover.data.spotify.api.response.playlists.ItemsInPlaylistResponseDto
import com.clovermusic.clover.data.spotify.api.response.playlists.PlaylistResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistService {

    @GET("me/playlists")
    suspend fun getCurrentUsersPlaylists(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 50
    ): CurrentUsersPlaylistResponseDto


    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistItems(
        @Path("playlist_id") playlistId: String,
        @Query("offset") offset: Int? = null
    ): ItemsInPlaylistResponseDto

    @GET("playlists/{playlist_id}")
    suspend fun getPlaylist(
        @Path("playlist_id") playlistId: String,
    ): PlaylistResponseDto

    @POST("playlists/{playlist_id}/tracks")
    suspend fun addItemsToPlaylist(
        @Path("playlist_id") playlistId: String,
        @Body uris: Array<String>
    )

    @DELETE("playlists/{playlist_id}/tracks")
    suspend fun removePlaylistItems(
        @Path("playlist_id") playlistId: String,
        @Query("tracks") tracks: List<String>
    )

    @POST("users/{user_id}/playlists")
    suspend fun createPlaylist(
        @Path("user_id") userId: String,
        @Body body: CreatePlaylistRequest
    ): PlaylistResponseDto

    @PUT("playlists/{playlist_id}/images")
    suspend fun uploadPlaylistCoverImage(
        @Path("playlist_id") playlistId: String,
        @Body image: String // Base64-encoded JPEG image data
    ): ImageResponseDto
}