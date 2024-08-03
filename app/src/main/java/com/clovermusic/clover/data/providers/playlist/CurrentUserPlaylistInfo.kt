package com.clovermusic.clover.data.providers.playlist

import android.util.Log
import com.clovermusic.clover.data.local.dao.PlaylistDao
import com.clovermusic.clover.data.local.entity.PlaylistInfoEntity
import com.clovermusic.clover.data.local.mapper.toPlaylistsEntity
import com.clovermusic.clover.data.providers.CacheDuration.CACHE_DURATION
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentUserPlaylistInfo @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val playlistDao: PlaylistDao,
) {
    suspend operator fun invoke(forceRefresh: Boolean): List<PlaylistInfoEntity> =
        withContext(Dispatchers.IO) {
            try {
                val storedData: List<PlaylistInfoEntity> = playlistDao.getAllPlaylists()
                val currentTime = System.currentTimeMillis()

                val shouldRefresh = forceRefresh || storedData.isEmpty() ||
                        storedData.any { currentTime - it.timestamp > CACHE_DURATION }

                if (shouldRefresh) {
                    Log.i("CurrentUserPlaylistInfo", "Fetching new playlists")
                    val res = playlistRepository.getCurrentUsersPlaylists()
                    playlistDao.insertPlaylist(res.toPlaylistsEntity())
                    res.toPlaylistsEntity()
                } else {
                    Log.i("CurrentUserPlaylistInfo", "Returning cached playlists")
                    storedData
                }
            } catch (e: Exception) {
                Log.e("CurrentUserPlaylistInfo", "Error fetching playlists", e)
                throw e
            }
        }
}