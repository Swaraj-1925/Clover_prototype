package com.clovermusic.clover.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistWithDetails
import com.clovermusic.clover.data.local.entity.relations.PlaylistWithTrack
import kotlinx.coroutines.flow.Flow

@Dao
interface ProvideDataDao {

    @Query("SELECT (SELECT COUNT(*) FROM playlist) == 0")
    suspend fun isPlaylistTableEmpty(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM playlist_track) == 0")
    suspend fun isTrackTableEmpty(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM albums) == 0")
    suspend fun isAlbumTableEmpty(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM user) == 0")
    suspend fun isUserTableEmpty(): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM artists) == 0")
    suspend fun isArtistTableEmpty(): Boolean

//    @Transaction
//    @Query("SELECT * FROM playlist WHERE playlistId = :playlistId")
//    suspend fun getPlaylistWithTracksAndArtists(playlistId: String): PlaylistInfoWithDetails?

    @Query("SELECT * FROM playlist WHERE playlistId = :playlistId")
    fun getPlaylistInfo(playlistId: String): Flow<PlaylistInfoEntity?>

    @Transaction
    @Query("SELECT * FROM playlist WHERE playlistId = :playlistId")
    fun getPlaylistWithTracks(playlistId: String): Flow<PlaylistWithTrack>

    @Transaction
    @Query("SELECT * FROM playlist WHERE playlistId = :playlistId")
    fun getPlaylistWithDetails(playlistId: String): PlaylistWithDetails?

    @Query("SELECT * FROM playlist")
    fun getAllPlaylists(): List<PlaylistInfoEntity>

    @Query("SELECT * FROM playlist WHERE userId = :userId")
    fun getPlaylistsByUserId(userId: String): Flow<List<PlaylistInfoEntity>>

    @Query("SELECT * FROM artists WHERE isFollowed = 1")
    fun getFollowedArtists(): List<ArtistsEntity>

    @Query("SELECT * FROM artists WHERE isTopArtist = 1")
    fun getTopArtists(): List<ArtistsEntity>

    @Query("SELECT * FROM albums WHERE artistId = :artistId")
    fun getAlbum(artistId: String): List<AlbumEntity>

    @Query("SELECT * FROM playlist_track WHERE trackId = :trackId")
    fun getSingleTrack(trackId: String): Flow<TrackEntity?>

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserData(userId: String): Flow<UserEntity?>
}
