package com.clovermusic.clover.data.spotify.network


import com.clovermusic.clover.data.spotify.models.playlist.currentUserPlaylists.CurrentUserPlaylistResponse
import com.clovermusic.clover.data.spotify.models.playlist.playlistTracks.PlaylistTracksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistApiServices {

    @GET("me/playlists")
    fun getCurrentUserPlaylists(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 50
    ): CurrentUserPlaylistResponse

    @GET("v1/playlists/{playlist_id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("playlist_id") playlistId: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 100
    ): PlaylistTracksResponse
}