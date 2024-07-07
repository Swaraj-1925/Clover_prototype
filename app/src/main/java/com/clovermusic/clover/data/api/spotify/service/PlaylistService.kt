package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.common.PlaylistTrackResponseDto
import com.clovermusic.clover.data.api.spotify.response.playlists.CurrentUsersPlaylistResponseDto
import com.clovermusic.clover.data.api.spotify.response.playlists.ItemsInPlaylistResponseDto
import retrofit2.http.GET
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
    ): PlaylistTrackResponseDto

}