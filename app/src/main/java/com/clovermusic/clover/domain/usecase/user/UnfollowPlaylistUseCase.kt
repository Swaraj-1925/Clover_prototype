package com.clovermusic.clover.domain.usecase.user

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class UnfollowPlaylistUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
//    suspend operator fun invoke(playlistId: String) {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            repository.unfollowPlaylist(playlistId)
//        }.onFailure { e ->
//            Log.e("UnfollowPlaylistUseCase", "Error unfollowing playlist", e)
//        }.getOrThrow()
//    }
}