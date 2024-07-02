package com.clovermusic.clover.domain.spotify.repository

import com.clovermusic.clover.data.spotify.models.user.followedArtists.FollowedArtistsResponse
import com.clovermusic.clover.data.spotify.models.user.topItems.TopArtistsResponse

interface UserRepository {

    suspend fun followedArtists(): FollowedArtistsResponse
    suspend fun topArtists(): TopArtistsResponse

}