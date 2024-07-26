package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import javax.inject.Inject

class AddItemsToPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(playlistId: String, uris: List<String>) {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.addItemsToPlaylist(playlistId, uris)

        }.onFailure { e ->
            Log.e("AddItemsToPlaylistUseCase", "Error fetching playlist items", e)
        }.getOrThrow()
    }
}