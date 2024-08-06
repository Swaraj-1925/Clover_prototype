package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.dao.InsertDataDao
import com.clovermusic.clover.data.local.dao.ProvideDataDao
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val insertDao: InsertDataDao,
    private val provideDao: ProvideDataDao
) {
    suspend fun getArtistAlbums(
        artistId: String,
        limit: Int?,
        forceRefresh: Boolean
    ): Flow<DataState<List<AlbumEntity>>> = flow {

        val storedAlbums = provideDao.getAlbum(artistId)
        if (storedAlbums.isNotEmpty() || forceRefresh) {
            Log.d("DatabaseCheck", "Stored Artist:")
            emit(DataState.OldData(storedAlbums))
        }
        val fetchedAlbums = dataSource.artistData.fetchArtistAlbums(artistId, limit)
        val albumEntities = fetchedAlbums.toEntity(artistId)
        if (storedAlbums != albumEntities) {
            Log.d("DatabaseCheck", "online Playlists:")
            insertDao.upsertAlbums(albumEntities)
            emit(DataState.NewData(albumEntities))
        }
        emit(DataState.Error("An unknown error occurred"))
    }.flowOn(Dispatchers.IO) // Ensure emissions happen in IO context
        .catch { e -> emit(DataState.Error(e.message ?: "An unknown error occurred")) }
}