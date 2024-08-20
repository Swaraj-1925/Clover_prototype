package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class CreateNewPlaylistUseCase @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
//    suspend operator fun invoke(
//        userId: String,
//        playlistRequest: CreatePlaylistRequest
//    ): Playlist {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            val createdPlaylist = repository.createNewPlaylist(userId, playlistRequest)
//            createdPlaylist.toPlaylist()
//        }.onFailure { e ->
//            Log.e("CreateNewPlaylistUseCase", "Error creating new playlist", e)
//        }.getOrThrow()
//    }
}