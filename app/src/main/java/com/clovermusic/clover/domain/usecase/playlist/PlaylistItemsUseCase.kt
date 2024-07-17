package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toPlaylistItems
import com.clovermusic.clover.domain.model.PlaylistItems
import javax.inject.Inject

class PlaylistItemsUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    //    Get items in playlist for a playlist
    suspend operator fun invoke(playlistId: String): List<PlaylistItems> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.getPlaylistItems(playlistId).toPlaylistItems()
        }.onFailure { e ->
            Log.e("PlaylistItemsUseCase", "Error fetching playlist items", e)
        }.getOrThrow()
    }
}
