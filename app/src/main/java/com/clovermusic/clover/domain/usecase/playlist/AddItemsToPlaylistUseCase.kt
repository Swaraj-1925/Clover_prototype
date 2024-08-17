package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class AddItemsToPlaylistUseCase @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
//    suspend operator fun invoke(playlistId: String, uris: List<String>) {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            repository.addItemsToPlaylist(playlistId, uris)
//
//        }.onFailure { e ->
//            Log.e("AddItemsToPlaylistUseCase", "Error fetching playlist items", e)
//        }.getOrThrow()
//    }
}