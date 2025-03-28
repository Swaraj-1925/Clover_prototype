package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.dao.Insert
import com.clovermusic.clover.data.local.dao.Provide
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val insert: Insert,
    private val provide: Provide
) {
    suspend fun getAndStoreUserDataFromAPi(): UserEntity {
        try {
            val response = dataSource.userDataSource.fetchCurrentUsersProfile()
            Log.d("UserRepository", "getAndStoreUserDataFromAPi: $response")
            val userEntity = response.toEntity()
            insert.insertUser(userEntity)
            return userEntity
        } catch (e: Exception) {
            Log.e("UserRepository", "getAndStoreUserDataFromAPi: ", e)
            throw e
        }
    }

    fun getStoredUserData(): UserEntity {
        return try {
            provide.getUser()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAndStoreFollowedArtistsFromApi(): List<ArtistsEntity> {
        try {
            val response = dataSource.userDataSource.fetchFollowedArtists()
            val artistsEntity = response.toEntity().map { it.copy(isFollowed = true) }
            insert.upsertArtist(artistsEntity)
            return artistsEntity
        } catch (e: Exception) {
            Log.e("UserRepository", "getAndStoreFollowedArtistsFromApi: ", e)
            throw e
        }
    }

    fun getStoredFollowedArtists(): List<ArtistsEntity> {
        return try {
            provide.provideFollowedArtists()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAndStoreTopArtistsFromApi(timeRange: String): List<ArtistsEntity> {
        try {
            val response = dataSource.userDataSource.fetchTopArtists(timeRange)
            val artistsEntity = response.toEntity().map { it.copy(isTopArtist = true) }
            insert.upsertArtist(artistsEntity)
            return artistsEntity
        } catch (e: Exception) {
            Log.e("UserRepository", "getAndStoreTopArtistsFromApi: ", e)
            throw e
        }
    }

    fun getStoredTopArtists(): List<ArtistsEntity> {
        return try {
            provide.provideTopArtists()
        } catch (e: Exception) {
            throw e
        }
    }
}