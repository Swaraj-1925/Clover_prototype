package com.clovermusic.clover.domain.usecase.playback

import android.util.Log
import com.clovermusic.clover.data.spotify.appRemote.SpotifyAppRemoteRepository
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RemotePlaybackHandlerUseCase @Inject constructor(
    private val appRemoteRepository: SpotifyAppRemoteRepository
) {
    suspend fun performRemoteAction(action: suspend (SpotifyAppRemote) -> Unit) {
        try {
            val remote = appRemoteRepository.getConnectedAppRemote()
            action(remote)
        } catch (e: Exception) {
            Log.e("RemotePlaybackHandlerUseCase", "Error performing remote action", e)
            throw e
        }
    }

    suspend fun playMusic(uri: String) = performRemoteAction { it.playerApi.play(uri) }
    suspend fun pauseMusic() = performRemoteAction { it.playerApi.pause() }
    suspend fun resumeMusic() = performRemoteAction { it.playerApi.resume() }
    suspend fun skipToNext() = performRemoteAction { it.playerApi.skipNext() }
    suspend fun skipToPrevious() = performRemoteAction { it.playerApi.skipPrevious() }
    suspend fun toggleShuffle() = performRemoteAction { it.playerApi.toggleShuffle() }
    suspend fun toggleRepeat() = performRemoteAction { it.playerApi.toggleRepeat() }
    suspend fun seekTo(position: Long) = performRemoteAction { it.playerApi.seekTo(position) }
    suspend fun isMusicPlaying(): Boolean = coroutineScope {
        try {
            var isPlaying = true
            performRemoteAction { remote ->
                remote.playerApi.playerState.setResultCallback { playerState ->
                    isPlaying = !playerState.isPaused
                }
            }
            isPlaying
        } catch (e: Exception) {
            Log.e("RemotePlaybackHandlerUseCase", "Error checking if music is playing", e)
            throw e
        }
    }

}
