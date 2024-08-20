package com.clovermusic.clover.domain.usecase.user

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class CheckIfUserFollowsUsersUseCases @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
//    suspend operator fun invoke(ids: List<String>): List<Boolean> {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            val res = repository.checkIfUserFollowsArtistsOrUsers("user", ids)
//            res
//        }.onFailure { e ->
//            Log.e("CheckIfUserFollowsUsersUseCases", "Error checking if user follows users", e)
//        }.getOrThrow()
//    }
}