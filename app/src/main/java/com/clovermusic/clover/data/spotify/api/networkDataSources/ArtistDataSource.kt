package com.clovermusic.clover.data.spotify.api.networkDataSources


import android.util.Log
import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackItemsResponseDto
import com.clovermusic.clover.data.spotify.api.service.ArtistService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtistDataSource @Inject constructor(
    private val artistService: ArtistService
) {

    suspend fun fetchArtistAlbums(artistId: String, limits: Int?): List<AlbumResponseDto> =
        withContext(Dispatchers.IO) {
            val artistAlbums =
                mutableListOf<AlbumResponseDto>()
            var offset = 0
            var total: Int
            do {
                val response =
                    artistService.getArtistAlbums(artistId, offset = offset)
                artistAlbums.addAll(response.items)
                total = response.total
                offset += response.limit
                if (limits != null && total > limits) {
                    if (offset > limits) {
                        break
                    }
                }
                Log.d(
                    "ArtistRepository ",
                    "fetchArtistAlbums: fetched batch, size: ${response.total} and offset: $offset"
                )
            } while (offset < total)

            Log.d(
                "ArtistRepository ",
                "fetchArtistAlbums: Success, total albums: ${artistAlbums.size}"
            )
            artistAlbums
        }

    suspend fun fetchArtistTopTracks(artistId: String): List<TrackItemsResponseDto> =
        withContext(Dispatchers.IO) {
            val topTrack =
                mutableListOf<TrackItemsResponseDto>()
            val response = artistService.getArtistTopTracks(artistId)
            topTrack.addAll(response.tracks)

            Log.d(
                "ArtistRepository ",
                "fetchArtistTopTracks: Success, total albums: ${topTrack.size}"
            )
            topTrack

        }

    suspend fun fetchArtistRelatedArtists(id: String): List<TrackArtistResponseDto> =
        withContext(Dispatchers.IO) {
            val artists =
                mutableListOf<TrackArtistResponseDto>()
            val response = artistService.getArtistRelatedArtists(id)
            artists.addAll(response.artists)
            artists
        }
}