package com.clovermusic.clover.domain.usecase.playback

import android.util.Log
import com.clovermusic.clover.data.spotify.appRemote.SpotifyAppRemoteRepository
import com.spotify.android.appremote.api.SpotifyAppRemote
import javax.inject.Inject

class RemotePlaybackHandlerUseCase @Inject constructor(
    private val appRemote: SpotifyAppRemoteRepository
) {
    suspend fun connectToRemote(): SpotifyAppRemote {
        return runCatching {
            val appRemote = appRemote.connectToAppRemote()
            appRemote
        }.onFailure { e ->
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
        }.getOrThrow()
    }

    fun playMusic(remote: SpotifyAppRemote, uri: String) {
        appRemote.assertAppRemoteConnected(remote)
            .playerApi.play(uri)
    }
}