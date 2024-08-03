package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.providers.Providers
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import javax.inject.Inject

class CurrentUsersPlaylistsUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val providers: Providers
) {
    //    Get current users(Logged in) playlists
    suspend operator fun invoke(forceRefresh: Boolean): List<PlaylistInfoEntity> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val res = providers.currentUserPlaylistInfo(forceRefresh)
            res
        }.onFailure { e ->
            Log.e("TopArtistUseCase", "Error fetching top artists", e)
        }.getOrThrow()
    }
}