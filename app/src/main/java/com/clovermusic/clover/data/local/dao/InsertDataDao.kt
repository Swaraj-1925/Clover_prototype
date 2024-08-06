package com.clovermusic.clover.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Upsert
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.CollaboratorsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.TrackArtistsCrossRef

@Dao
interface InsertDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetTrack(track: List<TrackEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistInfo(playlist: List<PlaylistInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistInfo(playlist: PlaylistInfoEntity)

    @Upsert
    suspend fun upsertPlaylists(playlists: List<PlaylistInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetCollaborators(collaborator: List<CollaboratorsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetArtist(artist: List<ArtistsEntity>)

    @Upsert
    suspend fun upsertArtists(artists: List<ArtistsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetAlbum(album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetAlbum(album: List<AlbumEntity>)

    @Upsert
    suspend fun upsertAlbums(albums: List<AlbumEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetCollaborator(album: List<CollaboratorsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTrackCrossRef(crossRefs: List<PlaylistTrackCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTrackCrossRef(crossRef: PlaylistTrackCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackArtistsCrossRef(crossRefs: List<TrackArtistsCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollaboratorsTrackCrossRef(crossRefs: List<CollaboratorsTrackCrossRef>)
}
