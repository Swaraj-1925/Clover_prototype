package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class GetPlaylistItemsUseCase @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
//    //    Get items in playlist for a playlist
//    suspend operator fun invoke(playlistId: String): List<PlaylistItems> {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            repository.getPlaylistItems(playlistId).toPlaylistItems()
//        }.onFailure { e ->
//            Log.e("PlaylistItemsUseCase", "Error fetching playlist items", e)
//        }.getOrThrow()
//    }
}
