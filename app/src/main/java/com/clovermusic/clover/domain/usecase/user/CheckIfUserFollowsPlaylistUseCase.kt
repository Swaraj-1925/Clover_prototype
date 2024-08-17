package com.clovermusic.clover.domain.usecase.user

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class CheckIfUserFollowsPlaylistUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
//    suspend operator fun invoke(playlistId: String): Boolean {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            val res = repository.checkIfCurrentUserFollowsPlaylist(playlistId)
//            res
//        }.onFailure { e ->
//            Log.e("CheckIfUserFollowsUsersUseCases", "Error checking if user follows users", e)
//        }.getOrThrow()
//    }
}