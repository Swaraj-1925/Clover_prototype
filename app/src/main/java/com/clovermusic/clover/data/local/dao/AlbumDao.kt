package com.clovermusic.clover.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clovermusic.clover.data.local.entity.AlbumEntity

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE artistsId = :artistId")
    suspend fun getAlbumsByArtist(artistId: String): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE uri = :uri")
    suspend fun getAlbumByUri(uri: String): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(albums: List<AlbumEntity>)
}