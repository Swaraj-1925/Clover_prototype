package com.clovermusic.clover.data.repository

import android.util.Log
import com.clovermusic.clover.data.api.spotify.response.ArtistAlbumsResponseItem
import com.clovermusic.clover.data.api.spotify.service.ArtistService
import com.clovermusic.clover.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistService: ArtistService
) {
    suspend fun getArtistAlbums(artistId: String): Flow<Resource<List<ArtistAlbumsResponseItem>>> =
        flow {
            emit(Resource.Loading())

            val artistAlbums = mutableListOf<ArtistAlbumsResponseItem>()
            try {
                var response = artistService.getNewReleases(artistId)
                artistAlbums.addAll(response.items)
                while (response.next != null) {
                    response = artistService.getNextPage(response.next)
                    artistAlbums.addAll(response.items)
                }
                Log.i("ArtistRepository", "getArtistAlbums : Success")
                emit(Resource.Success(artistAlbums))

            } catch (e: Exception) {
                Log.e("ArtistRepository", "getArtistAlbums Exception: ", e)
                emit(Resource.Error("Unknown error. Please contact support for assistance."))
            } catch (e: IOException) {
                Log.e("ArtistRepository", "getArtistAlbums IOException: ${e.message}", e)
                emit(Resource.Error("Network error occurred during authentication. Please try again later."))
            }

        }
}