package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.common.AlbumResponseDto
import com.clovermusic.clover.data.api.spotify.response.common.TrackItemsResponseDto
import com.clovermusic.clover.data.api.spotify.service.ArtistService
import java.io.IOException
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistService: ArtistService
) {
    suspend fun getArtistAlbums(artistId: String): List<AlbumResponseDto> {
        val artistAlbums = mutableListOf<AlbumResponseDto>()
        var offset = 0
        val limit = 50
        var total: Int
        try {
            do {
                val response =
                    artistService.getArtistAlbums(artistId, offset = offset, limit = limit)
                artistAlbums.addAll(response.items)
                total = response.total
                offset += limit
                Log.d(
                    "ArtistRepository ",
                    "getArtistAlbums: fetched batch, size: ${response.total} and offset: $offset"
                )
            } while (offset < total)

            Log.d(
                "ArtistRepository ",
                "getArtistAlbums: Success, total albums: ${artistAlbums.size}"
            )
            return artistAlbums
        } catch (e: IOException) {
            Log.e("ArtistRepository", "Network error while fetching playlists", e)
            throw ArtistFetchException("Network error occurred while fetching playlists", e)
        } catch (e: Exception) {
            Log.e("ArtistRepository", "Unexpected error while fetching playlists", e)
            throw ArtistFetchException("An unexpected error occurred while fetching playlists", e)
        }
    }

    suspend fun getArtistTopTracks(artistId: String): List<TrackItemsResponseDto> {

        val topTrack = mutableListOf<TrackItemsResponseDto>()
        try {
            val response = artistService.getArtistTopTracks(artistId)
            topTrack.addAll(response.tracks)

            Log.d(
                "ArtistRepository ",
                "getArtistTopTracks: Success, total albums: ${topTrack.size}"
            )
            return topTrack

        } catch (e: IOException) {
            Log.e("ArtistRepository", "Network error while fetching playlists", e)
            throw ArtistFetchException("Network error occurred while fetching playlists", e)
        } catch (e: Exception) {
            Log.e("ArtistRepository", "Unexpected error while fetching playlists", e)
            throw ArtistFetchException("An unexpected error occurred while fetching playlists", e)
        }
    }

    class ArtistFetchException(message: String, cause: Throwable? = null) :
        Exception(message, cause)
}



