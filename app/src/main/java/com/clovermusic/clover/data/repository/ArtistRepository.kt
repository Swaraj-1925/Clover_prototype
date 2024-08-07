package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.dao.Insert
import com.clovermusic.clover.data.local.dao.Provide
import com.clovermusic.clover.data.local.entity.relations.ArtistWithAlbums
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val dataSource: NetworkDataSource,
    private val insert: Insert,
    private val provide: Provide
) {
    suspend fun getAndStoreArtistAlbumsFromApi(
        artistId: String,
        limit: Int?
    ): ArtistWithAlbums {
        try {
            val response =
                dataSource.artistData.fetchArtistAlbums(artistId = artistId, limits = limit)
            val albumEntities = response.toEntity(artistId)
            insert.insertAlbum(albumEntities)

            return provide.provideArtistAlbum(artistId)
        } catch (e: Exception) {
            Log.e("ArtistRepository", "getAndStoreArtistAlbumsFromApi: ", e)
            throw e
        }
    }

    fun getStoredArtistAlbums(artistId: String): ArtistWithAlbums {
        return try {
            provide.provideArtistAlbum(artistId)
        } catch (e: Exception) {
            throw e
        }
    }

}