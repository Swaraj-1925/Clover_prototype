package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistWithDetails
import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val repository: Repository,
    private val authRepository: SpotifyAuthRepository
) {
    // Get a single playlist and all details about it

    suspend operator fun invoke(
        forceRefresh: Boolean,
        playlistId: String,
    ): PlaylistWithDetails {
        return runCatching {
            authRepository.ensureValidAccessToken()
            
            val playlistFlow = repository.playlists.getPlaylist(playlistId, forceRefresh)
            val playlistInfo = playlistFlow.first().let { dataState ->
                when (dataState) {
                    is DataState.NewData -> dataState.data
                    is DataState.OldData -> dataState.data
                    is DataState.Error -> {
                        throw Exception(dataState.message)
                    }
                }
            }

            // Ensure playlistInfo is not null before returning
            playlistInfo ?: throw Exception("Playlist not found")
        }.onFailure { e ->
            Log.e("GetPlaylistUseCase", "Error fetching playlist", e)
        }.getOrThrow()
    }
}
