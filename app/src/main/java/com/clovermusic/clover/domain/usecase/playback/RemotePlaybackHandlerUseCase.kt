package com.clovermusic.clover.domain.usecase.playback

import android.util.Log
import com.clovermusic.clover.data.spotify.appRemote.SpotifyAppRemoteRepository
import com.spotify.android.appremote.api.SpotifyAppRemote
import javax.inject.Inject

class RemotePlaybackHandlerUseCase @Inject constructor(
    private val appRemoteRepository: SpotifyAppRemoteRepository
) {
    suspend fun connectToRemote(): SpotifyAppRemote {
        return runCatching {
            appRemoteRepository.connectToAppRemote()
        }.onFailure { e ->
            Log.e("RemotePlaybackHandlerUseCase", "Error connecting to Spotify app remote", e)
        }.getOrThrow()
    }

    suspend fun playMusic(remote: SpotifyAppRemote, uri: String) {
        performRemoteAction(remote) {
            it.playerApi.play(uri)
        }
    }

    suspend fun pauseMusic(remote: SpotifyAppRemote) {
        performRemoteAction(remote) {
            it.playerApi.pause()
        }
    }

    suspend fun resumeMusic(remote: SpotifyAppRemote) {
        performRemoteAction(remote) {
            it.playerApi.resume()
        }
    }

    suspend fun skipToNext(remote: SpotifyAppRemote) {
        performRemoteAction(remote) {
            it.playerApi.skipNext()
        }
    }

    suspend fun skipToPrevious(remote: SpotifyAppRemote) {
        performRemoteAction(remote) {
            it.playerApi.skipPrevious()
        }
    }

    suspend fun toggleShuffle(remote: SpotifyAppRemote) {
        performRemoteAction(remote) {
            it.playerApi.toggleShuffle()
        }
    }

    suspend fun toggleRepeat(remote: SpotifyAppRemote) {
        performRemoteAction(remote) {
            it.playerApi.toggleRepeat()
        }
    }

    suspend fun isMusicPlaying(remote: SpotifyAppRemote, callback: (Boolean) -> Unit) {
        performRemoteAction(remote) {
            it.playerApi.playerState.setResultCallback { playerState ->
                callback(!playerState.isPaused)
            }
        }
    }


    private suspend fun performRemoteAction(
        remote: SpotifyAppRemote,
        action: (SpotifyAppRemote) -> Unit
    ) {
        try {
            action(appRemoteRepository.assertAppRemoteConnected(remote))
        } catch (e: Exception) {
            Log.e("RemotePlaybackHandlerUseCase", "Error performing remote action", e)
        }
    }
}
