package com.clovermusic.clover.data.spotify.api.networkDataAction

import android.util.Log
import com.clovermusic.clover.data.spotify.api.dto.common.ImageResponseDto
import com.clovermusic.clover.data.spotify.api.dto.playlists.CreatePlaylistRequest
import com.clovermusic.clover.data.spotify.api.dto.playlists.PlaylistResponseDto
import com.clovermusic.clover.data.spotify.api.service.PlaylistService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistDataAction @Inject constructor(
    private val playlistService: PlaylistService
) {

    suspend fun addItemsToPlaylist(playlistId: String, uris: List<String>): Unit =
        withContext(Dispatchers.IO) {
            playlistService.addItemsToPlaylist(playlistId, uris.toTypedArray())
            Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
        }

    suspend fun removePlaylistItems(playlistId: String, uri: List<String>): Unit =
        withContext(Dispatchers.IO) {
            playlistService.removePlaylistItems(playlistId, uri)
            Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
        }

    suspend fun createNewPlaylist(
        userId: String,
        playlistRequest: CreatePlaylistRequest
    ): PlaylistResponseDto =
        withContext(Dispatchers.IO) {
            val createdPlaylist = playlistService.createPlaylist(userId, playlistRequest)
            Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
            createdPlaylist
        }

    suspend fun uploadPlaylistCover(playlistId: String, image: String): ImageResponseDto =
        withContext(Dispatchers.IO) {
            val playlist = playlistService.uploadPlaylistCoverImage(playlistId, image)
            Log.d("UserRepository", "followPlaylist: Successfully followed playlist")
            playlist
        }

}