package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.repository.UserRepository
import javax.inject.Inject
// Tells Whether user follow the artist or not
class CheckIfUserFollowsArtistUseCases @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: UserRepository
) {
    suspend operator fun invoke(ids: List<String>): List<Boolean> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val res = repository.checkIfUserFollowsArtistsOrUsers("artist", ids)
            res
        }.onFailure { e ->
            Log.e("CheckIfUserFollowsUsersUseCases", "Error checking if user follows users", e)
        }.getOrThrow()
    }
}