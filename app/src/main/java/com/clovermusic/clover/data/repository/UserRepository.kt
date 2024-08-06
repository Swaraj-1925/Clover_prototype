package com.clovermusic.clover.data.repository

import com.clovermusic.clover.data.local.dao.InsertDataDao
import com.clovermusic.clover.data.local.dao.ProvideDataDao
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val insertDao: InsertDataDao,
    private val provideDao: ProvideDataDao
) {
    suspend fun getFollowedArtists(forceRefresh: Boolean): Flow<DataState<List<ArtistsEntity>>> =
        flow {
            try {
                val storedArtists = provideDao.getFollowedArtists()
                if (storedArtists.isEmpty() || forceRefresh) {
                    val res = dataSource.userDataSource.fetchFollowedArtists()
                    val artistsEntity = res.toEntity(followed = true)
                    insertDao.upsertArtists(artistsEntity)
                    emit(DataState.NewData(artistsEntity))
                } else {
                    emit(DataState.OldData(storedArtists))
                    val res = dataSource.userDataSource.fetchFollowedArtists()
                    if (storedArtists != res.toEntity(followed = true)) {

                        val artistsEntity = res.toEntity(followed = true)
                        insertDao.upsertArtists(artistsEntity)
                        emit(DataState.NewData(artistsEntity))
                    } else {
                        emit(DataState.OldData(storedArtists))
                    }
                }
            } catch (e: Exception) {
                emit(DataState.Error(e.message ?: "An unknown error occurred"))
            }
        }.flowOn(Dispatchers.IO) // Ensure emissions happen in IO context
            .catch { e -> emit(DataState.Error(e.message ?: "An unknown error occurred")) }

    suspend fun getTopArtists(forceRefresh: Boolean): Flow<DataState<List<ArtistsEntity>>> = flow {
        try {
            val storedArtists = provideDao.getTopArtists()

            if (storedArtists.isEmpty() || forceRefresh) {
                val res = dataSource.userDataSource.fetchFollowedArtists()

                val artistsEntity = res.toEntity(top = true)
                insertDao.upsertArtists(artistsEntity)

                emit(DataState.NewData(artistsEntity))
            } else {
                emit(DataState.OldData(storedArtists))

                val res = dataSource.userDataSource.fetchFollowedArtists()
                if (storedArtists != res.toEntity(top = true)) {

                    val artistsEntity = res.toEntity(top = true)
                    insertDao.upsertArtists(artistsEntity)
                    emit(DataState.NewData(artistsEntity))
                } else {
                    emit(DataState.OldData(storedArtists))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "An unknown error occurred"))
        }

    }.flowOn(Dispatchers.IO) // Ensure emissions happen in IO context
        .catch { e -> emit(DataState.Error(e.message ?: "An unknown error occurred")) }
}