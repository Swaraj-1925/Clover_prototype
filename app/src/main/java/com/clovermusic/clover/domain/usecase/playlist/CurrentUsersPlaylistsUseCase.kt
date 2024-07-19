package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toUserPlaylist
import com.clovermusic.clover.domain.model.UserPlaylist
import javax.inject.Inject

class CurrentUsersPlaylistsUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    //    Get current users(Logged in) playlists
    suspend operator fun invoke(): List<UserPlaylist> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            repository.getCurrentUsersPlaylists().toUserPlaylist()
        }.onFailure { e ->
            Log.e("TopArtistUseCase", "Error fetching top artists", e)
        }.getOrThrow()
    }
}