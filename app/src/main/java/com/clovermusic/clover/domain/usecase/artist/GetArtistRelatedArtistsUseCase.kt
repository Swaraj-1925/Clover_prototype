package com.clovermusic.clover.domain.usecase.artist

import android.util.Log
import com.clovermusic.clover.data.spotify.api.networkDataAction.NetworkDataAction
import com.clovermusic.clover.data.spotify.api.repository.ArtistRepository
import com.clovermusic.clover.domain.mapper.Util.toTrackArtists
import com.clovermusic.clover.domain.model.common.TrackArtists
import javax.inject.Inject

class GetArtistRelatedArtistsUseCase @Inject constructor(
    private val networkDataAction: NetworkDataAction,
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(id: String): List<TrackArtists> {
        return runCatching {
            networkDataAction.authData.ensureValidAccessToken()
            val res = repository.getArtistRelatedArtists(id)
            res.toTrackArtists()
        }.onFailure { e ->
            Log.e("ArtistAlbumsUseCase", "Error fetching albums for artists", e)
        }.getOrThrow()
    }
}