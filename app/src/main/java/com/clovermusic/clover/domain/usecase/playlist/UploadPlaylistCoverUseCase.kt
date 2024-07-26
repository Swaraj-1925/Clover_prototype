package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.Util.toImages
import com.clovermusic.clover.domain.model.common.Image
import javax.inject.Inject

class UploadPlaylistCoverUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    /**
     * this function Upload playlist cover to Spotify and return its url the image
     *  need to be of Base64-encoded JPEG image data
     **/
    suspend operator fun invoke(playlistId: String, image: String): Image {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val coverUrl = repository.uploadPlaylistCover(playlistId, image)
            coverUrl.toImages()
        }.onFailure { e ->
            Log.e("PlaylistUseCase", "Error fetching playlist", e)
        }.getOrThrow()
    }
}