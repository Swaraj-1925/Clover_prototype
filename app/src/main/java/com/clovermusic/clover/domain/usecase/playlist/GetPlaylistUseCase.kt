package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.providers.Providers
import com.clovermusic.clover.data.providers.playlist.Playlist
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val providers: Providers,
    private val authRepository: SpotifyAuthRepository
) {
    //    Get a single playlist and all details about it
    suspend operator fun invoke(
        forceRefresh: Boolean,
        playlistId: String,
    ): Playlist? {
        return runCatching {
            authRepository.ensureValidAccessToken()
            providers.getPlaylist(
                forceRefresh = forceRefresh,
                playlistId = playlistId,
            )
        }.onFailure { e ->
            Log.e("PlaylistUseCase", "Error fetching playlist", e)
        }.getOrThrow()
    }
}