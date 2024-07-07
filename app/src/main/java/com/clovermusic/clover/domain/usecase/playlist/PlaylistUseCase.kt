package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toPlaylistItems
import com.clovermusic.clover.domain.model.PlaylistItem
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(playlistId: String): Flow<Resource<List<PlaylistItem>>> = flow {
        emit(Resource.Loading())
        try {
            authRepository.ensureValidAccessToken(
                onTokenRefreshed = {
                    val playlistItems = repository.getPlaylist(playlistId)
                    if (playlistItems.isNotEmpty()) {
                        emit(Resource.Success(toPlaylistItems(playlistItems)))
                    } else {
                        emit(Resource.Error("No playlist items found"))
                    }
                },
                onError = { error ->
                    emit(Resource.Error("Failed to refresh token: $error"))
                }
            )
        } catch (e: Exception) {
            emit(Resource.Error("An error occurred while fetching playlist items"))
            Log.e("GetPlaylistItemsUseCase", "Error getting data: ${e.message}")
        }
    }
}