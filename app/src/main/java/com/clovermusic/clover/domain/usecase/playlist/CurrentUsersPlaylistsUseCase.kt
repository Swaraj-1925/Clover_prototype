package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class CurrentUsersPlaylistsUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val repository: Repository
) {
    //    Get current users(Logged in) playlists

    suspend operator fun invoke(forceRefresh: Boolean): List<PlaylistInfoEntity> {
        return runCatching {
            authRepository.ensureValidAccessToken()
            val playlist = mutableListOf<PlaylistInfoEntity>()

            val flow = repository.playlists.getCurrentUserPlaylist()
            flow.toList().let { dataStates ->
                dataStates.forEach { dataState ->
                    when (dataState) {
                        is DataState.NewData -> {
                            playlist.clear()
                            playlist.addAll(dataState.data)
                        }

                        is DataState.OldData -> playlist.addAll(dataState.data)
                        is DataState.Error -> Log.e(
                            "CurrentUsersPlaylistsUseCase",
                            "Error fetching playlists: ${dataState.message}"
                        )
                    }
                }
            }

            playlist
        }.onFailure { e ->
            Log.e("CurrentUsersPlaylistsUseCase", "Error fetching playlists", e)
        }.getOrThrow()
    }
}