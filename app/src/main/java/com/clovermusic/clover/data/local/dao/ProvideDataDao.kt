package com.clovermusic.clover.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.local.entity.relations.ArtistWithAlbums
import com.clovermusic.clover.data.local.entity.relations.Playlist

@Dao
interface ProvideDataDao {

    @Query("SELECT * FROM user")
    fun getUser(): UserEntity

    @Query("SELECT * FROM playlist")
    fun provideAllPlaylistInfo(): List<PlaylistInfoEntity>

    @Query("SELECT * FROM playlist WHERE playlistId = :playlistId")
    fun providePlaylistInfo(playlistId: String): PlaylistInfoEntity

    @Query("SELECT * FROM playlist WHERE playlistId = :playlistId")
    fun providePlaylist(playlistId: String): Playlist

    @Query("SELECT * FROM artists WHERE artistId = :artistId")
    fun provideArtistAlbum(artistId: String): ArtistWithAlbums

    @Query(
        """
        SELECT COUNT(*)
        FROM playlist
        INNER JOIN PlaylistTrackCrossRef ON playlist.playlistId = PlaylistTrackCrossRef.playlistId
        WHERE playlist.playlistId = :playlistId
    """
    )
    fun playlistHasTracks(playlistId: String): Int

    @Query("SELECT * FROM artists WHERE isFollowed = 1")
    fun provideFollowedArtists(): List<ArtistsEntity>

    @Query("SELECT * FROM artists WHERE isTopArtist = 1")
    fun provideTopArtists(): List<ArtistsEntity>


}
