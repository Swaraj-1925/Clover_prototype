package com.clovermusic.clover.data.spotify.repository

import com.clovermusic.clover.data.spotify.models.user.followedArtists.FollowedArtistsResponse
import com.clovermusic.clover.data.spotify.models.user.topItems.TopArtistsResponse
import com.clovermusic.clover.data.spotify.network.UserApiService
import com.clovermusic.clover.domain.spotify.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override suspend fun followedArtists(): FollowedArtistsResponse {
        return apiService.getfollowedArtists()
    }

    override suspend fun topArtists(): TopArtistsResponse {
        return apiService.getTopArtists()
    }

}