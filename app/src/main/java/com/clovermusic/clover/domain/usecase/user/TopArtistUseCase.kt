package com.clovermusic.clover.domain.usecase.user

import android.util.Log
import com.clovermusic.clover.data.repository.UserRepository
import com.clovermusic.clover.domain.mapper.toFollowedArtists
import com.clovermusic.clover.domain.model.FollowedArtists
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopArtistUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<FollowedArtists>>> = flow {
        val response = repository.getTopArtists()
        response.collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    val artists = toFollowedArtists(resource.data)
                    emit(Resource.Success(artists))
                }

                is Resource.Error -> {
                    Log.e(
                        "FollowArtistsUseCase",
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