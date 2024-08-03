package com.clovermusic.clover.data.providers.artist

import android.util.Log
import com.clovermusic.clover.data.local.dao.AlbumDao
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.local.mapper.toAlbumsEntity
import com.clovermusic.clover.data.providers.CacheDuration.CACHE_DURATION
import com.clovermusic.clover.data.spotify.api.repository.ArtistRepository
import com.clovermusic.clover.util.Parsers.parseReleaseDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class Albums @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val albumDao: AlbumDao
) {

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    suspend operator fun invoke(
        forceRefresh: Boolean = false,
        artistsId: String,
        limit: Int?
    ): List<AlbumEntity> =
        withContext(Dispatchers.IO) {
            try {
                Log.i("Albums", "artist uri $artistsId")
                val storedData: List<AlbumEntity> = albumDao.getAlbumsByArtist(artistsId)

                Log.i("Albums", "Stored data $storedData")
                val currentTime = System.currentTimeMillis()
                val todayDate = LocalDate.now().format(dateFormat)

                val shouldRefresh = forceRefresh || storedData.isEmpty() ||
                        storedData.any {
                            currentTime - it.timestamp > CACHE_DURATION ||
                                    parseReleaseDate(it.releaseDate).format(dateFormat) != todayDate
                        }

                if (shouldRefresh) {
                    Log.i("Albums", "Fetching new Albums")
                    val res = artistRepository.getArtistAlbums(artistsId, limit)
                    val newAlbums = res.toAlbumsEntity()
                    albumDao.insertAllAlbums(newAlbums)
                    newAlbums
                } else {
                    Log.i("Albums", "Returning cached Albums")
                    storedData
                }
            } catch (e: Exception) {
                throw e
            }
        }
}
