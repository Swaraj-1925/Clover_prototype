package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import javax.inject.Inject

class AddItemsToPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val networkDataAction: NetworkDataAction
) {
    suspend operator fun invoke(playlistId: String, uris: List<String>) {
        return runCatching {
            networkDataAction.authData.ensureValidAccessToken()
            repository.addItemsToPlaylist(playlistId, uris)

        }.onFailure { e ->
            Log.e("AddItemsToPlaylistUseCase", "Error fetching playlist items", e)
        }.getOrThrow()
    }
}