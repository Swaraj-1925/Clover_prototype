package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.dao.Insert
import com.clovermusic.clover.data.local.dao.Provide
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.TrackArtistsCrossRef
import com.clovermusic.clover.data.local.entity.relations.Playlist
import com.clovermusic.clover.data.local.entity.relations.TrackWithArtists
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PlaylistRepository @Inject constructor(
    private val provide: Provide,
    private val insert: Insert,
    private val dataSource: NetworkDataSource
) {

    suspend fun getAndStoreCurrentUserPlaylistsFromApi(): List<PlaylistInfoEntity> {
        try {
            val response = dataSource.playlistData.fetchCurrentUsersPlaylists()
            val playlistEntities = response.toEntity()
            insert.insertPlaylistInfo(playlistEntities)
            return playlistEntities
        } catch (e: Exception) {
            Log.e("PlaylistRepository", "getAndStoreCurrentUserPlaylistsFromApi: ", e)
            throw e
        }
    }

    fun getStoredCurrentUserPlaylists(): List<PlaylistInfoEntity> {
        return try {
            provide.provideAllPlaylistInfo()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAndStorePlaylistFromApi(playlistId: String): Playlist =
        withContext(Dispatchers.IO) {
            try {
                val response = async { dataSource.playlistData.fetchPlaylist(playlistId) }.await()

                val playlistEntity = response.toEntity()
                val trackEntities = response.tracks.items.toEntity()
                val artistEntities = response.tracks.items.flatMap { it.track.artists }.toEntity()
                val collaboratorsEntities =
                    response.tracks.items.map { it.added_by.toEntity(it.track.id) }

                val playlistTrackCrossRefs = response.tracks.items.map {
                    PlaylistTrackCrossRef(
                        playlistId = playlistEntity.playlistId,
                        trackId = it.track.id
                    )
                }

                val trackArtistCrossRefs = response.tracks.items.flatMap { trackResponse ->
                    trackResponse.track.artists.map { artistResponse ->
                        TrackArtistsCrossRef(
                            trackId = trackResponse.track.id,
                            artistId = artistResponse.id
                        )
                    }
                }
                val trackCollaboratorCrossRefs = response.tracks.items.map {
                    CollaboratorsTrackCrossRef(
                        trackId = it.track.id,
                        collaboratorId = it.added_by.id
                    )
                }
                insert.apply {
                    insertPlaylistInfo(playlistEntity)
                    insertTrack(trackEntities)
                    insertArtist(artistEntities)
                    insertCollaborator(collaboratorsEntities)
                    insertPlaylistTrackCrossRef(playlistTrackCrossRefs)
                    insertTrackArtistsCrossRef(trackArtistCrossRefs)
                    insertCollaboratorsTrackCrossRef(trackCollaboratorCrossRefs)
                }

                provide.providePlaylist(playlistId)
            } catch (e: Exception) {
                Log.e("PlaylistRepository", "getAndStorePlaylistFromApi: ", e)
                throw e
            }
        }

    suspend fun getStoredPlaylist(playlistId: String): Playlist? = withContext(Dispatchers.IO) {
        try {
            val playlistHasTracks = provide.playlistHasTracks(playlistId) > 0
            if (playlistHasTracks) {
                provide.providePlaylist(playlistId)
            } else {
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun getPlaylistFromApi(playlistId: String): Playlist {
        val response = dataSource.playlistData.fetchPlaylist(playlistId)
        val playlistEntity = response.toEntity()

        val trackWithArtistsList = response.tracks.items.map { data ->
            val trackEntity = data.track.toEntity()
            val artistsEntity = data.track.artists.toEntity()

            TrackWithArtists(
                track = trackEntity,
                artists = artistsEntity
            )
        }

        return Playlist(
            playlist = playlistEntity,
            tracks = trackWithArtistsList
        )
    }
}
