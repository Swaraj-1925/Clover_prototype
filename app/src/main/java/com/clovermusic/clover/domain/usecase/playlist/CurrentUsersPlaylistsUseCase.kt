package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.data.repository.SpotifyAuthRepository
import com.clovermusic.clover.domain.mapper.toCurrentUserPlaylist
import com.clovermusic.clover.domain.model.CurrentUserPlaylist
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CurrentUsersPlaylistsUseCase @Inject constructor(
    private val repository: PlaylistRepository,
    private val authRepository: SpotifyAuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<CurrentUserPlaylist>>> = flow {
        emit(Resource.Loading())
        try {
            authRepository.ensureValidAccessToken(
                onTokenRefreshed = {
                    val playlists = repository.getCurrentUsersPlaylists()

                    if (playlists.isNotEmpty()) {
                        emit(Resource.Success(toCurrentUserPlaylist(playlists)))
                    } else {
                        emit(Resource.Error("No playlists found"))
                    }
                },
                onError = { error ->
                    emit(Resource.Error("Failed to refresh token: $error"))
                }
            )
        } catch (e: IOException) {
            Log.e("GetCurrentUsersPlaylistsUseCase", "Network error: ${e.message}")
            emit(Resource.Error("Network error occurred. Please try again later."))
        } catch (e: Exception) {
            Log.e("GetCurrentUsersPlaylistsUseCase", "Error getting data: ${e.message}")
            emit(Resource.Error("An error occurred while fetching playlists"))
        }
    }
}