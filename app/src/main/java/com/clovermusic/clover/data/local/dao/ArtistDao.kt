package com.clovermusic.clover.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clovermusic.clover.data.local.entity.ArtistsEntity

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artists")
    suspend fun getAllArtists(): List<ArtistsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistsEntity>)

    @Query("SELECT * FROM artists WHERE isFollowed = 1")
    suspend fun getAllFollowedArtists(): List<ArtistsEntity>

    @Query("SELECT * FROM artists WHERE isTopArtist = 1")
    suspend fun getTopArtists(): List<ArtistsEntity>
}