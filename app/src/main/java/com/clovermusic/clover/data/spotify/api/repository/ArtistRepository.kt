package com.clovermusic.clover.data.spotify.api.repository

import android.util.Log
import com.clovermusic.clover.data.local.entity.TrackEntity
import com.clovermusic.clover.data.repository.mappers.toEntity
import com.clovermusic.clover.data.spotify.api.dto.common.AlbumResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackArtistResponseDto
import com.clovermusic.clover.data.spotify.api.dto.common.TrackItemsResponseDto
import com.clovermusic.clover.data.spotify.api.service.ArtistService
import com.clovermusic.clover.util.CustomException
import com.clovermusic.clover.util.SpotifyApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// Repository for Artist related api call
class ArtistRepository @Inject constructor(
    private val artistService: ArtistService
) {
    /**
     * Function will get artist all albums if no limit is present if limit is present then it will
     * get just that many albums i.e until it reaches that offset
     */
    suspend fun getArtistAlbums(
        artistId: String,
        limits: Int?
    ): List<AlbumResponseDto> =
        withContext(Dispatchers.IO) {
            val artistAlbums =
                mutableListOf<AlbumResponseDto>()
            var offset = 0
            var total: Int
            try {
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
                        "getArtistAlbums: fetched batch, size: ${response.total} and offset: $offset"
                    )
                } while (offset < total)

                Log.d(
                    "ArtistRepository ",
                    "getArtistAlbums: Success, total albums: ${artistAlbums.size}"
                )
                artistAlbums
            } catch (e: IOException) {
                throw CustomException.NetworkException("ArtistRepository", "getArtistAlbums", e)
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "ArtistRepository",
                    "getArtistAlbums",
                    "Unknown error",
                    e
                )
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException("ArtistRepository", "getArtistAlbums", e)
            }
        }

    //    Function to get artist top tracks
    suspend fun getArtistTopTracks(artistId: String): List<TrackEntity> =
        withContext(Dispatchers.IO) {
            val topTrack =
                mutableListOf<TrackEntity>()
            try {
                val response = artistService.getArtistTopTracks(artistId)
                topTrack.addAll(response.tracks.toEntity())

                Log.d(
                    "ArtistRepository ",
                    "getArtistTopTracks: Success, total albums: ${topTrack.size}"
                )

                topTrack

            } catch (e: IOException) {
                throw CustomException.NetworkException("ArtistRepository", "getArtistTopTracks", e)
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "ArtistRepository",
                    "getArtistTopTracks",
                    "Unknown error",
                    e
                )
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException("ArtistRepository", "getArtistTopTracks", e)
            }
        }

    suspend fun getArtistRelatedArtists(id: String): List<TrackArtistResponseDto> =
        withContext(Dispatchers.IO) {
            val artists =
                mutableListOf<TrackArtistResponseDto>()
            try {
                val response = artistService.getArtistRelatedArtists(id)
                artists.addAll(response.artists)
                artists
            } catch (e: IOException) {
                throw CustomException.NetworkException("ArtistRepository", "getArtistTopTracks", e)
            } catch (e: Exception) {
                throw CustomException.UnknownException(
                    "ArtistRepository",
                    "getArtistTopTracks",
                    "Unknown error",
                    e
                )
            } catch (e: HttpException) {
                SpotifyApiException.handleApiException("ArtistRepository", "getArtistTopTracks", e)
            }
        }

}


