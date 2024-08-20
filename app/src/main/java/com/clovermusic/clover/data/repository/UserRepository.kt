package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.dao.Insert
import com.clovermusic.clover.data.local.dao.Provide
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val insert: Insert,
    private val provide: Provide
) {
    suspend fun getAndStoreUserDataFromAPi(): UserEntity = withContext(Dispatchers.IO) {
        try {
            val response = dataSource.userDataSource.fetchCurrentUsersProfile()
            Log.d("UserRepository", "getAndStoreUserDataFromAPi: $response")
            val userEntity = response.toEntity()
            insert.insertUser(userEntity)
            userEntity
        } catch (e: Exception) {
            Log.e("UserRepository", "getAndStoreUserDataFromAPi: ", e)
            throw e
        }
    }

    suspend fun getStoredUserData(): UserEntity = withContext(Dispatchers.IO) {
        try {
            provide.getUser()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAndStoreFollowedArtistsFromApi(): List<ArtistsEntity> =
        withContext(Dispatchers.IO) {
            try {
                val response = dataSource.userDataSource.fetchFollowedArtists()

                val artistsEntity = response.toEntity().map { it.copy(isFollowed = true) }

                artistsEntity.forEach { artist ->
                    val existingArtist = provide.getArtistById(artist.artistId)
                    if (existingArtist != null) {
                        insert.upsertArtist(
                            existingArtist.copy(
                                isFollowed


                                = true,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                    } else {
                        insert.insertArtist(artist)
                    }
                }
                artistsEntity
            } catch (e: Exception) {
                Log.e("UserRepository", "getAndStoreFollowedArtistsFromApi: ", e)
                throw e
            }
        }

    suspend fun getStoredFollowedArtists(): List<ArtistsEntity> = withContext(Dispatchers.IO) {
        try {
            provide.provideFollowedArtists()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAndStoreTopArtistsFromApi(timeRange: String): List<ArtistsEntity> =
        withContext(Dispatchers.IO) {
            try {
                val response = dataSource.userDataSource.fetchTopArtists(timeRange)
                val artistsEntity = response.toEntity().map { it.copy(isTopArtist = true) }

                artistsEntity.forEach { artist ->
                    val existingArtist = provide.getArtistById(artist.artistId)
                    if (existingArtist != null) {
                        insert.upsertArtist(
                            existingArtist.copy(
                                isTopArtist = true,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                    } else {
                        insert.insertArtist(artist)
                    }
                }
                artistsEntity
            } catch (e: Exception) {
                Log.e("UserRepository", "getAndStoreTopArtistsFromApi: ", e)
                throw e
            }
        }

    suspend fun getStoredTopArtists(): List<ArtistsEntity> = withContext(Dispatchers.IO) {
        try {
            provide.provideTopArtists()
        } catch (e: Exception) {
            throw e
        }
    }
}