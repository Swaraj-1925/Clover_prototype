package com.clovermusic.clover.data.providers.user

import android.util.Log
import com.clovermusic.clover.data.local.dao.ArtistDao
import com.clovermusic.clover.data.local.entity.ArtistsEntity
import com.clovermusic.clover.data.local.mapper.toArtistEntity
import com.clovermusic.clover.data.providers.CacheDuration.CACHE_DURATION
import com.clovermusic.clover.data.spotify.api.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopArtists @Inject constructor(
    private val userRepository: UserRepository,
    private val artistDao: ArtistDao,
) {

    suspend operator fun invoke(forceRefresh: Boolean, timeRange: String): List<ArtistsEntity> =
        withContext(Dispatchers.IO) {
            try {
                val storedData: List<ArtistsEntity> = artistDao.getTopArtists()
                val currentTime = System.currentTimeMillis()

                val shouldRefresh = forceRefresh || storedData.isEmpty() ||
                        storedData.any { currentTime - it.timestamp > CACHE_DURATION }

                if (shouldRefresh) {
                    Log.i("FollowedArtists", "Fetching new artists")
                    val res = userRepository.getTopArtists(timeRange)
                    artistDao.insertArtists(res.toArtistEntity(isTopArtist = true))
                    val data = artistDao.getTopArtists()
                    data.filter { it.isTopArtist }
                } else {
                    Log.i("FollowedArtists", "Returning cached Followed Artists")
                    storedData
                }
            } catch (e: Exception) {
                throw e
            }
        }
}
