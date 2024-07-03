package com.clovermusic.clover.domain.usecase.playlist

import android.util.Log
import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.domain.mapper.toCurrentUserPlaylist
import com.clovermusic.clover.domain.model.CurrentUserPlaylist
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrentUsersPlaylistsUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<CurrentUserPlaylist>>> = flow {
        emit(Resource.Loading())

        val response = repository.getCurrentUsersPlaylists()
        response.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    val playlist = toCurrentUserPlaylist(resource.data)
                    emit(Resource.Success(playlist))
                }

                is Resource.Error -> {
                    Log.e(
                        "CurrentUsersPlaylistsUseCase",
                        "Error Getting data: ${resource.message}"
                    )
                    emit(Resource.Error("Unknown error. Please contact support for assistance."))
                }

                is Resource.Loading -> {
                    // You can handle loading state if needed
                    emit(Resource.Loading())
                }
            }

        }
    }
}