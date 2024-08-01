package com.clovermusic.clover.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clovermusic.clover.data.local.entity.PlaylistEntity
import com.clovermusic.clover.data.local.entity.PlaylistTrackEntity

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM playlist")
    suspend fun getAllPlaylists(): List<PlaylistEntity>

    @Query("SELECT * FROM playlist WHERE uri = :playlistUri")
    suspend fun getPlaylist(playlistUri: String): PlaylistEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: List<PlaylistEntity>)

    @Query("SELECT * FROM playlist_track WHERE playlistUri = :playlistUri")
    suspend fun getPlaylistTracks(playlistUri: String): List<PlaylistTrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<PlaylistTrackEntity>)

    @Query("DELETE FROM playlist_track WHERE playlistUri = :playlistUri")
    suspend fun deleteTracksByPlaylist(playlistUri: String)
}