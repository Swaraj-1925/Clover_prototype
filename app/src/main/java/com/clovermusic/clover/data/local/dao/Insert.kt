package com.clovermusic.clover.data.local.dao

import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.entity.CollaboratorsEntity
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.local.entity.UserEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.TrackArtistsCrossRef
import javax.inject.Inject

class Insert @Inject constructor(
    private val insertData: InsertDataDao
) : InsertDataDao {
    override fun insertUser(user: UserEntity) {
        insertData.insertUser(user)
    }


    override fun insertPlaylistInfo(playlistInfo: PlaylistInfoEntity) {
        insertData.insertPlaylistInfo(playlistInfo)
    }

    override fun insertPlaylistInfo(playlistInfo: List<PlaylistInfoEntity>) {
        insertData.insertPlaylistInfo(playlistInfo)
    }

    override fun insertTrack(track: TrackEntity) {
        insertData.insertTrack(track)
    }

    override fun insertTrack(track: List<TrackEntity>) {
        insertData.insertTrack(track)
    }

    override fun insertArtist(artist: ArtistsEntity) {
        insertData.insertArtist(artist)
    }

    override fun insertArtist(artist: List<ArtistsEntity>) {
        insertData.insertArtist(artist)
    }

    override fun upsertArtist(artist: ArtistsEntity) {
        insertData.upsertArtist(artist)
    }

    override fun upsertArtist(artist: List<ArtistsEntity>) {
        insertData.upsertArtist(artist)
    }

    override fun insertAlbum(album: AlbumEntity) {
        insertData.insertAlbum(album)
    }

    override fun insertAlbum(album: List<AlbumEntity>) {
        insertData.insertAlbum(album)
    }

    override fun insertCollaborator(collaborator: CollaboratorsEntity) {
        insertData.insertCollaborator(collaborator)
    }

    override fun insertCollaborator(collaborator: List<CollaboratorsEntity>) {
        insertData.insertCollaborator(collaborator)
    }

    override fun insertPlaylistTrackCrossRef(crossRef: PlaylistTrackCrossRef) {
        insertData.insertPlaylistTrackCrossRef(crossRef)
    }

    override fun insertPlaylistTrackCrossRef(crossRef: List<PlaylistTrackCrossRef>) {
        insertData.insertPlaylistTrackCrossRef(crossRef)
    }

    override fun insertTrackArtistsCrossRef(crossRef: TrackArtistsCrossRef) {
        insertData.insertTrackArtistsCrossRef(crossRef)
    }

    override fun insertTrackArtistsCrossRef(crossRef: List<TrackArtistsCrossRef>) {
        insertData.insertTrackArtistsCrossRef(crossRef)
    }

    override fun insertCollaboratorsTrackCrossRef(crossRef: CollaboratorsTrackCrossRef) {
        insertData.insertCollaboratorsTrackCrossRef(crossRef)
    }

    override fun insertCollaboratorsTrackCrossRef(crossRef: List<CollaboratorsTrackCrossRef>) {
        insertData.insertCollaboratorsTrackCrossRef(crossRef)
    }
}