package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import javax.inject.Inject

class RemovePlaylistItemsUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val networkDataAction: NetworkDataAction
) {
    suspend operator fun invoke(playlistId: String, tracks: List<String>) {
        return runCatching {
            networkDataAction.authData.ensureValidAccessToken()
            repository.removePlaylistItems(playlistId, tracks)
        }.onFailure { e ->
            Log.e("RemovePlaylistItemsUseCase", "Error removing playlist items", e)
        }.getOrThrow()
    }
}