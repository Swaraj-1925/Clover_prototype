package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toPlaylist
import com.clovermusic.clover.domain.model.Playlist
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    //    Get a single playlist and all details about it
    suspend operator fun invoke(playlistId: String): Playlist {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.getPlaylist(playlistId).toPlaylist()
        }.onFailure { e ->
            Log.e("PlaylistUseCase", "Error fetching playlist", e)
        }.getOrThrow()
    }
}