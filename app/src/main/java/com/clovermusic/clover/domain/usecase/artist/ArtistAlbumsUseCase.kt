package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.repository.ArtistRepository
import com.clovermusic.clover.domain.mapper.toArtistAlbums
import com.clovermusic.clover.domain.model.ArtistAlbums
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArtistAlbumsUseCase @Inject constructor(
    private val artistRepository: ArtistRepository,
) {

    suspend operator fun invoke(artistId: List<String>): Flow<Resource<List<ArtistAlbums>>> = flow {

        emit(Resource.Loading())
        try {
            artistId.map { id ->
                val response = artistRepository.getArtistAlbums(id)
                response.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val albums = toArtistAlbums(resource.data)
                            emit(Resource.Success(albums))
                        }

                        is Resource.Error -> {
                            Log.e(
                                "GetArtistAlbumsUseCase",
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
        } catch (e: Exception) {
            Log.e("GetArtistAlbumsUseCase", "Error Getting data: ${e.message}")
        }

    }

}