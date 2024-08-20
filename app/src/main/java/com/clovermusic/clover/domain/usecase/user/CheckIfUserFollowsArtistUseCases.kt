package com.clovermusic.clover.domain.usecase.user

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class CheckIfUserFollowsArtistUseCases @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
//    suspend operator fun invoke(ids: List<String>): List<Boolean> {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            val res = repository.checkIfUserFollowsArtistsOrUsers("artist", ids)
//            res
//        }.onFailure { e ->
//            Log.e("CheckIfUserFollowsUsersUseCases", "Error checking if user follows users", e)
//        }.getOrThrow()
//    }
}