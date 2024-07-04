package com.clovermusic.clover.domain.usecase.playlist

import com.clovermusic.clover.data.repository.PlaylistRepository
import com.clovermusic.clover.domain.mapper.toItemPlaylist
import com.clovermusic.clover.domain.model.PlaylistItem
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistItemsUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: String): Flow<Resource<List<PlaylistItem>>> = flow {
        emit(Resource.Loading())

        val response = repository.getPlaylistItems(playlistId)

        response.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    try {
                        val playlistItem = toItemPlaylist(resource.data!!)
                        emit(Resource.Success(playlistItem))
                    } catch (e: Exception) {
                        emit(Resource.Error("Failed to map playlist items: ${e.message}"))
                    }
                }

                is Resource.Error -> {
                    emit(Resource.Error(resource.message ?: "Unknown error occurred"))
                }

                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
            }
        }
    }
}
