package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.entity.relations.AlbumWithTrack
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val dataSource: NetworkDataSource,
) {
    suspend fun getAlbum(albumId: String): AlbumWithTrack =
        withContext(Dispatchers.IO) {
            try {
                dataSource.albumData.fetchAlbum(albumId = albumId).toEntity(null)
            } catch (e: Exception) {
                Log.d("AlbumRepository", "getAlbum ", e)
                throw e
            }
        }
}