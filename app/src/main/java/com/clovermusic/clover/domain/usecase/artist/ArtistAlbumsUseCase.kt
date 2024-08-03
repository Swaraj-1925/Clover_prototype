package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.local.entity.AlbumEntity
import com.clovermusic.clover.data.providers.Providers
import com.clovermusic.clover.data.spotify.api.repository.SpotifyAuthRepository
import javax.inject.Inject

class ArtistAlbumsUseCase @Inject constructor(
    private val authRepository: SpotifyAuthRepository,
    private val providers: Providers
) {
    /**
     *  get albums for artists set limit to null to get all albums
     */
    suspend operator fun invoke(
        artistIds: List<String>,
        limit: Int?,
        forceRefresh: Boolean
    ): List<AlbumEntity> {
        return runCatching {
            val albums = mutableListOf<AlbumEntity>()
            authRepository.ensureValidAccessToken()
            artistIds.map { artistId ->
                val res = providers.albums(artistsId = artistId, limit = limit)
                albums.addAll(res)
            }
            Log.i("ArtistAlbumsUseCase", "getArtistAlbums: Success, total albums: $albums")
            albums
        }.onFailure { e ->
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
        }.getOrThrow()
    }
}