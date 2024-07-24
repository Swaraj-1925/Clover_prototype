package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import javax.inject.Inject

class FollowPlaylistUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: UserRepository
) {
    suspend operator fun invoke(playlistId: String) {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.followPlaylist(playlistId)
        }.onFailure { e ->
            Log.e("FollowPlaylistUseCase", "Error following playlist", e)
        }.getOrThrow()
    }
}