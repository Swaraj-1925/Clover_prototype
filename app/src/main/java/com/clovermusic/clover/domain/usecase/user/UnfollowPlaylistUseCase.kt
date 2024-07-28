package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import javax.inject.Inject
// Unfollowing a playlist
class UnfollowPlaylistUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: UserRepository
) {
    suspend operator fun invoke(playlistId: String) {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.unfollowPlaylist(playlistId)
        }.onFailure { e ->
            Log.e("UnfollowPlaylistUseCase", "Error unfollowing playlist", e)
        }.getOrThrow()
    }
}