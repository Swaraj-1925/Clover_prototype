package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import javax.inject.Inject

class RemovePlaylistItemsUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(playlistId: String, tracks: List<String>) {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.removePlaylistItems(playlistId, tracks)
        }.onFailure { e ->
            Log.e("RemovePlaylistItemsUseCase", "Error removing playlist items", e)
        }.getOrThrow()
    }
}