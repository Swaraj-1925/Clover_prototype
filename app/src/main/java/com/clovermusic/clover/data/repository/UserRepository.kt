package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.dao.Insert
import com.clovermusic.clover.data.local.dao.Provide
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.SearchResultEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.dto.search.SearchResponseDto
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import com.clovermusic.clover.domain.model.Search
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
            val response = dataSource.userData.fetchCurrentUsersProfile()
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
                val response = dataSource.userData.fetchFollowedArtists()

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
                val response = dataSource.userData.fetchTopArtists(timeRange)
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

    suspend fun incrementNumClick(playlistId: String) = withContext(Dispatchers.IO) {
        insert.incrementNumClick(playlistId)
    }


    suspend fun search(query: String, type: List<String>, limit: Int): SearchResponseDto {
        return try {
            val searchType = getSearchTypes(type)
            Log.d("UserRepository", "search: $searchType")
            dataSource.userData.search(query, searchType, limit)
        } catch (e: Exception) {
            Log.e("UserRepository", "Search error: ", e)
            throw e
        }
    }

    suspend fun storeSearchResult(result: Search) = withContext(Dispatchers.IO) {
        val searchResults = mutableListOf<SearchResultEntity>()

        result.album?.let { albums ->
            searchResults.addAll(albums.map { it.toEntity() })
        }

        result.artist?.let { artists ->
            searchResults.addAll(artists.map { it.toEntity() })
        }

        result.track?.let { tracks ->
            searchResults.addAll(tracks.map { it.toEntity() })
        }

        result.playlist?.let { playlists ->
            searchResults.addAll(playlists.map { it.toEntity() })
        }

        insert.insertSearchResults(searchResults)
    }

    suspend fun getStoredSearchResults(): List<SearchResultEntity> = withContext(Dispatchers.IO) {
        try {
            provide.getAllSearchResults()
        } catch (e: Exception) {
            Log.e("UserRepository", "getStoredSearchResults: ", e)
            emptyList()
        }
    }

    private fun getSearchTypes(type: List<String>): String {
        return if (type.contains("All")) {
            "album,artist,playlist,track,show,episode,audiobook"
        } else {
            type.map { it.lowercase() }.joinToString(",")
        }
    }
}