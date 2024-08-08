package com.clovermusic.clover.domain.usecase.playback

import android.util.Log
import com.clovermusic.clover.data.spotify.appRemote.SpotifyAppRemoteRepository
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RemotePlaybackHandlerUseCase @Inject constructor(
    private val appRemoteRepository: SpotifyAppRemoteRepository
) {

    private var remote: SpotifyAppRemote? = null
    private val _playerState = MutableStateFlow<PlayerState?>(null)
    val playerState: StateFlow<PlayerState?> = _playerState.asStateFlow()

    suspend fun initialize() {
        if (remote == null) {
            remote = appRemoteRepository.getConnectedAppRemote()
            startPlayerStateUpdates()
        }
    }

    private fun startPlayerStateUpdates() {
        remote?.playerApi?.subscribeToPlayerState()?.setEventCallback { state ->
            _playerState.value = state
        }
    }

    suspend fun performRemoteAction(action: suspend (SpotifyAppRemote) -> Unit) {
        try {
            val remote = appRemoteRepository.getConnectedAppRemote()
            action(remote)
        } catch (e: Exception) {
            Log.e("RemotePlaybackHandlerUseCase", "Error performing remote action", e)
            throw e
        }
    }

    fun playMusic(uri: String) = remote?.playerApi?.play(uri)
    fun pauseMusic() = remote?.playerApi?.pause()
    fun resumeMusic() = remote?.playerApi?.resume()
    fun skipToNext() = remote?.playerApi?.skipNext()
    fun skipToPrevious() = remote?.playerApi?.skipPrevious()
    fun toggleShuffle() = remote?.playerApi?.toggleShuffle()
    fun toggleRepeat() = remote?.playerApi?.toggleRepeat()
    fun seekTo(position: Long) = remote?.playerApi?.seekTo(position)

    fun getCurrentPlaybackPosition(): Long = playerState.value?.playbackPosition ?: 0L
    fun getCurrentTrackDuration(): Long = playerState.value?.track?.duration ?: 0L
    fun isMusicPlaying(): Boolean = playerState.value?.isPaused == false

}
