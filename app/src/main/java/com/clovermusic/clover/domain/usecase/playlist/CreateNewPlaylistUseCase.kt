package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.spotify.api.dto.playlists.CreatePlaylistRequest
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import com.clovermusic.clover.domain.mapper.toPlaylist
import com.clovermusic.clover.domain.model.Playlist
import javax.inject.Inject

class CreateNewPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val networkDataAction: NetworkDataAction
) {
    suspend operator fun invoke(
        userId: String,
        playlistRequest: CreatePlaylistRequest
    ): Playlist {
        return runCatching {
            networkDataAction.authData.ensureValidAccessToken()
            val createdPlaylist = repository.createNewPlaylist(userId, playlistRequest)
            createdPlaylist.toPlaylist()
        }.onFailure { e ->
            Log.e("CreateNewPlaylistUseCase", "Error creating new playlist", e)
        }.getOrThrow()
    }
}