package com.clovermusic.clover.data.spotify.api.networkDataSources

import android.util.Log
import com.clovermusic.clover.data.spotify.api.dto.albums.AlbumTrackItemDto
import com.clovermusic.clover.data.spotify.api.dto.albums.SpecificAlbumResponseDto
import com.clovermusic.clover.data.spotify.api.service.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumDataSources @Inject constructor(
    private val albumService: AlbumService
) {
    suspend fun fetchAlbum(albumId: String): SpecificAlbumResponseDto =
        withContext(Dispatchers.IO) {
            Log.d("AlbumDataSources", "Fetching album $albumId")

            val album: SpecificAlbumResponseDto
            val response = albumService.getAlbum(albumId)
            val tracks = mutableListOf<AlbumTrackItemDto>().apply {
                addAll(response.tracks.items)
            }
            var nextUrl: String? = null

            album = response
            nextUrl = response.tracks.next
            while (nextUrl != null) {
                val response = albumService.getAlbumTrack(nextUrl)
                tracks.addAll(response.tracks.items)
                nextUrl = response.tracks.next
                Log.d(
                    "AlbumDataSources",
                    "Fetched batch ${response.tracks.items.size} from total ${response.tracks.total},"
                )
            }

            album
        }
}