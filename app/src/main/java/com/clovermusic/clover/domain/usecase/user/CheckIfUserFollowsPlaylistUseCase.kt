package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import javax.inject.Inject

class CheckIfUserFollowsPlaylistUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: UserRepository
) {
    suspend operator fun invoke(playlistId: String): Boolean {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val res = repository.checkIfCurrentUserFollowsPlaylist(playlistId)
            res
        }.onFailure { e ->
            Log.e("CheckIfUserFollowsUsersUseCases", "Error checking if user follows users", e)
        }.getOrThrow()
    }
}