package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.data.spotify.api.repository.UserRepository
import com.clovermusic.clover.domain.mapper.toUserProfile
import com.clovermusic.clover.domain.model.UserProfile
import javax.inject.Inject

class GetUsersProfileUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: String): UserProfile {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val res = repository.getUsersProfile(userId)
            res.toUserProfile()
        }.onFailure { e ->
            Log.e("GetUserProfileUseCase", "Error fetching user profile", e)
        }.getOrThrow()
    }

}