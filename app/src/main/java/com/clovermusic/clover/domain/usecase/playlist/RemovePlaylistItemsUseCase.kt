package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class RemovePlaylistItemsUseCase @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
//    suspend operator fun invoke(playlistId: String, tracks: List<String>) {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            repository.removePlaylistItems(playlistId, tracks)
//        }.onFailure { e ->
//            Log.e("RemovePlaylistItemsUseCase", "Error removing playlist items", e)
//        }.getOrThrow()
//    }
}