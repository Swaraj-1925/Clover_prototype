package com.clovermusic.clover.data.provider

import com.clovermusic.clover.data.local.dao.PlaylistDao
import com.clovermusic.clover.data.local.dao.Util
import com.clovermusic.clover.data.spotify.api.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryProvider @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val playlistDao: PlaylistDao,
    private val util: Util
) {

    suspend fun provideCurrentUsersPlaylists() {

    }
}