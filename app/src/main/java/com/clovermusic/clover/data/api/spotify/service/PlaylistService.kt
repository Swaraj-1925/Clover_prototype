package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.CurrentUsersPlaylistResponse
import com.clovermusic.clover.data.api.spotify.response.playlistResponseModels.ItemsInPlaylistResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PlaylistService {

    @GET("me/playlists")
    suspend fun getCurrentUsersPlaylists(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 50
    ): CurrentUsersPlaylistResponse


    @GET("playlists/{playlist_id}/tracks")
    suspend fun getPlaylistItems(
        @Path("playlist_id") playlistId: String,
        @Query("offset") offset: Int? = null
    ): ItemsInPlaylistResponse

    @GET
    suspend fun <T> getNextPage(@Url url: String?): T
}