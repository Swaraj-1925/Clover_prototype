package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class UploadPlaylistCoverUseCase @Inject constructor(
    private val repository: Repository,
    private val networkDataAction: NetworkDataAction
) {
    /**
     * this function Upload playlist cover to Spotify and return its url the image
     *  need to be of Base64-encoded JPEG image data
     **/
//    suspend operator fun invoke(playlistId: String, image: String): Image {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            val coverUrl = repository.uploadPlaylistCover(playlistId, image)
//            coverUrl.toImages()
//        }.onFailure { e ->
//            Log.e("PlaylistUseCase", "Error fetching playlist", e)
//        }.getOrThrow()
//    }
}