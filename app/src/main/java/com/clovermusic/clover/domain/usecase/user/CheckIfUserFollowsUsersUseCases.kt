package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.repository.UserRepository
import javax.inject.Inject

class CheckIfUserFollowsUsersUseCases @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: UserRepository
) {
    suspend operator fun invoke(ids: List<String>): List<Boolean> {
        return runCatching {
            networkDataAction.authData.ensureValidAccessToken()
            val res = repository.checkIfUserFollowsArtistsOrUsers("user", ids)
            res
        }.onFailure { e ->
            Log.e("CheckIfUserFollowsUsersUseCases", "Error checking if user follows users", e)
        }.getOrThrow()
    }
}