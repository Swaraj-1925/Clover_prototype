package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.repository.UserRepository
import javax.inject.Inject

class FollowPlaylistUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: UserRepository
) {
    suspend operator fun invoke(playlistId: String) {
        return runCatching {
            networkDataAction.authData.ensureValidAccessToken()
            repository.followPlaylist(playlistId)
        }.onFailure { e ->
            Log.e("FollowPlaylistUseCase", "Error following playlist", e)
        }.getOrThrow()
    }
}