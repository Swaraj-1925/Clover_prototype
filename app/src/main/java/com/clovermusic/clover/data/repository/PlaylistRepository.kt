package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.local.dao.InsertDataDao
import com.clovermusic.clover.data.local.dao.ProvideDataDao
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.crossRef.CollaboratorsTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistTrackCrossRef
import com.clovermusic.clover.data.local.entity.crossRef.PlaylistWithDetails
import com.clovermusic.clover.data.local.entity.crossRef.TrackArtistsCrossRef
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.networkDataSources.NetworkDataSource
import com.clovermusic.clover.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class PlaylistRepository @Inject constructor(
    private val provideDao: ProvideDataDao,
    private val insertDao: InsertDataDao,
    private val dataSource: NetworkDataSource // Assuming dataSource is provided
) {

    suspend fun getCurrentUserPlaylist(): Flow<DataState<List<PlaylistInfoEntity>>> = flow {
        try {
            // Emit stored playlists immediately
            val storedPlaylists = provideDao.getAllPlaylists()
            if (storedPlaylists.isNotEmpty()) {
                Log.d("DatabaseCheck", "Stored Playlists:")
                emit(DataState.OldData(storedPlaylists))
            }

            // Fetch new data from the remote source
//            val fetchedPlaylists =
//                dataSource.playlistData.fetchCurrentUsersPlaylists().toEntity()
//
//            // Compare fetched data with stored data
//            if (storedPlaylists != fetchedPlaylists) {
//                // Update the database
//                insertDao.upsertPlaylists(fetchedPlaylists)
//                emit(DataState.NewData(fetchedPlaylists))
//            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "An unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO) // Ensure emissions happen in IO context
        .catch { e -> emit(DataState.Error(e.message ?: "An unknown error occurred")) }

    suspend fun getPlaylist(
        playlistId: String,
        forceRefresh: Boolean
    ): Flow<DataState<PlaylistWithDetails>> = flow {
        try {
            val storedPlaylist = provideDao.getPlaylistWithDetails(playlistId)
            if (storedPlaylist == null || forceRefresh) {
                val fetchedPlaylist = fetchAndStorePlaylist(playlistId)
                emit(DataState.NewData(fetchedPlaylist))
            } else {
                emit(DataState.OldData(storedPlaylist))
                val fetchedPlaylist = fetchAndStorePlaylist(playlistId)
                if (storedPlaylist != fetchedPlaylist) {
                    emit(DataState.NewData(fetchedPlaylist))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "An unknown error occurred"))
        }

    }.flowOn(Dispatchers.IO) // Ensure emissions happen in IO context
        .catch { e -> emit(DataState.Error(e.message ?: "An unknown error occurred")) }

    private suspend fun fetchAndStorePlaylist(playlistId: String): PlaylistWithDetails {

        val playlistRes = dataSource.playlistData.fetchPlaylist(playlistId)

        val playlistEntity = playlistRes.toEntity()
        val trackEntities = playlistRes.tracks.items.map { it.track.toEntity() }

        val artistEntities =
            playlistRes.tracks.items.flatMap { it.track.artists }.toEntity()

        val albumEntities =
            playlistRes.tracks.items.map { it.track.album.toEntity(it.track.album.artists[0].id) }

        val collaboratorEntities = playlistRes.tracks.items.map { it.added_by.toEntity() }

        val playlistTrackCrossRefs = playlistRes.tracks.items.map {
            PlaylistTrackCrossRef(playlistId = playlistEntity.playlistId, trackId = it.track.id)
        }
        val trackArtistCrossRefs = playlistRes.tracks.items.flatMap { trackResponse ->
            trackResponse.track.artists.map { artistResponse ->
                TrackArtistsCrossRef(
                    trackId = trackResponse.track.id,
                    artistId = artistResponse.id
                )
            }
        }
        val trackCollaboratorCrossRefs = playlistRes.tracks.items.map {
            CollaboratorsTrackCrossRef(trackId = it.track.id, collaboratorId = it.added_by.id)
        }

        insertDao.apply {
            insertPlaylistInfo(playlistEntity)
            insetTrack(trackEntities)
            insetArtist(artistEntities)
            insetAlbum(albumEntities)
            insetCollaborator(collaboratorEntities)
            insertPlaylistTrackCrossRef(playlistTrackCrossRefs)
            insertTrackArtistsCrossRef(trackArtistCrossRefs)
            insertCollaboratorsTrackCrossRef(trackCollaboratorCrossRefs)
        }

        return provideDao.getPlaylistWithDetails(playlistId)
            ?: throw IllegalStateException("Failed to fetch and store playlist")
    }
}
