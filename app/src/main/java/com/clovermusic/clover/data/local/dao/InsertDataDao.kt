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
    fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylistInfo(playlistInfo: PlaylistInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylistInfo(playlistInfo: List<PlaylistInfoEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(track: List<TrackEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(artist: ArtistsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(artist: List<ArtistsEntity>)

    @Upsert
    fun upsertArtist(artist: ArtistsEntity)

    @Upsert
    fun upsertArtist(artist: List<ArtistsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: List<AlbumEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollaborator(collaborator: CollaboratorsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollaborator(collaborator: List<CollaboratorsEntity>)

    
//    CrossRef

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylistTrackCrossRef(crossRef: PlaylistTrackCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaylistTrackCrossRef(crossRef: List<PlaylistTrackCrossRef>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrackArtistsCrossRef(crossRef: TrackArtistsCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrackArtistsCrossRef(crossRef: List<TrackArtistsCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollaboratorsTrackCrossRef(crossRef: CollaboratorsTrackCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollaboratorsTrackCrossRef(crossRef: List<CollaboratorsTrackCrossRef>)


}