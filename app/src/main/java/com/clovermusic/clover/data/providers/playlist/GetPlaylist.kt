package com.clovermusic.clover.data.providers.playlist

import android.util.Log
import com.clovermusic.clover.data.local.dao.PlaylistDao
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.entity.PlaylistTrackEntity
import com.clovermusic.clover.data.local.mapper.toPlaylistTrackEntity
import com.clovermusic.clover.data.local.mapper.toPlaylistsEntity
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPlaylist @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val playlistDao: PlaylistDao,
) {
    suspend operator fun invoke(
        forceRefresh: Boolean,
        playlistId: String,
    ): Playlist =
        withContext(Dispatchers.IO) {
            try {
                val storedPlaylist = playlistDao.getPlaylist(playlistId)
                val storedTracks = playlistDao.getPlaylistTracks(playlistId)

                val shouldFetchNewData =
                    forceRefresh || storedPlaylist == null || storedTracks == null

                if (shouldFetchNewData) {
                    Log.i("CurrentUserPlaylistInfo", "Fetching new playlist")
                    fetchAndSaveNewPlaylist(playlistId)
                } else {
                    Log.i("CurrentUserPlaylistInfo", "Returning cached playlist")
                    Playlist(playlistInfo = storedPlaylist, tracks = storedTracks)
                }
            } catch (e: Exception) {
                Log.e("CurrentUserPlaylistInfo", "Error fetching playlist", e)
                throw e
            }
        }

    private suspend fun fetchAndSaveNewPlaylist(playlistId: String): Playlist {
        val newPlaylist = playlistRepository.getPlaylist(playlistId)
        val playlistEntity = newPlaylist.toPlaylistsEntity()
        val trackEntities = newPlaylist.tracks.items.toPlaylistTrackEntity(playlistId)

        // Insert the new playlist and tracks into the database
        playlistDao.insertPlaylist(playlistEntity)
        playlistDao.insertTracks(trackEntities)

        return Playlist(playlistInfo = playlistEntity, tracks = trackEntities)
    }
}

data class Playlist(
    val playlistInfo: PlaylistInfoEntity?,
    val tracks: List<PlaylistTrackEntity>?
)