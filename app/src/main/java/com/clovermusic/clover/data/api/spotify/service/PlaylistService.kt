package com.clovermusic.clover.data.api.spotify.service

import com.clovermusic.clover.data.api.spotify.response.CurrentUsersPlaylistsResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface PlaylistService {

    @GET("me/playlists?limit=50")
    suspend fun getCurrentUsersPlaylists(): CurrentUsersPlaylistsResponse


    @GET
    suspend fun <T> getNextPage(@Url url: String): T
}