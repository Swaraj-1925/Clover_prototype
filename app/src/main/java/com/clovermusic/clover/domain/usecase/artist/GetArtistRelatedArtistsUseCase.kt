package com.clovermusic.clover.domain.usecase.artist

import com.clovermusic.clover.data.repository.Repository
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import javax.inject.Inject

class GetArtistRelatedArtistsUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: Repository
) {
//    suspend operator fun invoke(id: String): List<TrackArtists> {
//        return runCatching {
//            networkDataAction.authData.ensureValidAccessToken()
//            val res = repository.getArtistRelatedArtists(id)
//            res.toTrackArtists()
//        }.onFailure { e ->
//            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
//        }.getOrThrow()
//    }
}