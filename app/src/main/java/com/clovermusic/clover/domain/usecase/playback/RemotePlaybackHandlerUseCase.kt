package com.clovermusic.clover.domain.usecase.playback

import android.util.Log
import com.clovermusic.clover.data.spotify.appRemote.SpotifyAppRemoteRepository
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemotePlaybackHandlerUseCase @Inject constructor(
    private val appRemoteRepository: SpotifyAppRemoteRepository
) {

    private var remote: SpotifyAppRemote? = null
    private val _playerState = MutableStateFlow<PlayerState?>(null)
    val playerState: StateFlow<PlayerState?> = _playerState.asStateFlow()

    private val _playbackPosition = MutableStateFlow(0L)
    val playbackPosition: StateFlow<Long> = _playbackPosition.asStateFlow()


    private var positionUpdateJob: Job? = null
    private var lastUpdateTime: Long = 0
    private var isPlaying: Boolean = false

    suspend fun initialize() {
        if (remote == null) {
            remote = appRemoteRepository.getConnectedAppRemote()
            startPlayerStateUpdates()
            startPositionUpdates()
        }
    }

    fun playMusic(uri: String) = remote?.playerApi?.play(uri)
    fun pauseMusic() = remote?.playerApi?.pause()
    fun resumeMusic() = remote?.playerApi?.resume()
    fun skipToNext() = remote?.playerApi?.skipNext()
    fun skipToPrevious() = remote?.playerApi?.skipPrevious()
    fun toggleShuffle() = remote?.playerApi?.toggleShuffle()
    fun toggleRepeat() = remote?.playerApi?.toggleRepeat()

    suspend fun seekTo(position: Long) {
        withContext(Dispatchers.IO) {
            remote?.playerApi?.seekTo(position)
            _playbackPosition.value = position
            lastUpdateTime = System.currentTimeMillis()
        }
    }

    private fun startPlayerStateUpdates() {
        remote?.playerApi?.subscribeToPlayerState()?.setEventCallback { state ->
            Log.d("RemotePlaybackHandler", "Player state updated")
            _playerState.value = state
            isPlaying = !state.isPaused
            _playbackPosition.value = state.playbackPosition
            lastUpdateTime = System.currentTimeMillis()
        }
    }

    //    Predict the value of slider based on the current playback position
    private fun startPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                if (isPlaying) {
                    val currentTime = System.currentTimeMillis()
                    val timeDifference = currentTime - lastUpdateTime
                    _playbackPosition.value += timeDifference
                    lastUpdateTime = currentTime
                }
                delay(100)
            }
        }
    }

    private suspend fun performRemoteAction(action: suspend (SpotifyAppRemote) -> Unit) {
        try {
            val remote = appRemoteRepository.getConnectedAppRemote()
            action(remote)
        } catch (e: Exception) {
            throw e
        }
    }

}
